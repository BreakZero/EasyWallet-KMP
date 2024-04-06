package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.Constants
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.model.FeeLevel
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull

class TransactionSigningUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val basicInfoUseCase: GetToKenBasicInfoUseCase,
    private val exactTokenRepositoryUseCase: GetExactTokenRepositoryUseCase
) {
    operator fun invoke(
        tokenId: String,
        chainId: String,
        toAddress: String,
        amount: String,
        feeLevel: FeeLevel
    ): Flow<String> {
        return combine(
            walletRepository.forActivatedOne().filterNotNull(),
            basicInfoUseCase(tokenId)
        ) { wallet, basicInfo ->
            val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
            val privateKey = getPrivateKeyFromChain(hdWallet, basicInfo)
            exactTokenRepositoryUseCase(basicInfo).signTransaction(
                chainId = chainId,
                privateKey = privateKey,
                contractAddress = basicInfo.contract,
                toAddress = toAddress,
                amount = amount,
                feeLevel = feeLevel
            )
        }
    }

    private fun getPrivateKeyFromChain(
        hdWallet: HDWallet,
        basicResult: TokenBasicResult
    ): ByteArray {
        val coinType = when (basicResult.chainName) {
            Constants.ETH_CHAIN_NAME -> CoinType.Ethereum
            else -> throw NotImplementedError("The chain(${basicResult.chainName}) not supported yet.")
        }
        return hdWallet.getKeyForCoin(coinType).data
    }
}
