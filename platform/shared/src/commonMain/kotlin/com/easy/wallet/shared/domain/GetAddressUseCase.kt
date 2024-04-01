package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.Constants
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAddressUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val supportedTokenRepository: SupportedTokenRepository
) {
    operator fun invoke(tokenId: String): Flow<TokenBasicResult> {
        return combine(
            supportedTokenRepository.findTokenByIdFlow(tokenId),
            walletRepository.forActivatedOne()
        ) { token, wallet ->
            val hdWallet = wallet?.let { HDWallet(it.mnemonic, it.passphrase) }
                ?: throw NoSuchElementException("No wallet had been set yet.")
            val foundToken =
                token ?: throw NoSuchElementException("No token found, id is: $tokenId")
            val address = when (token.chainName) {
                Constants.ETH_CHAIN_NAME -> {
                    hdWallet.getAddressForCoin(CoinType.Ethereum)
                }

                else -> ""
            }
            TokenBasicResult(
                tokenId = tokenId,
                symbol = foundToken.symbol,
                name = foundToken.name,
                decimals = foundToken.decimals,
                address = address
            )
        }
    }
}
