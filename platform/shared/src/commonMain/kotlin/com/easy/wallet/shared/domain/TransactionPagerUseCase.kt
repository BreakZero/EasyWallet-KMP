package com.easy.wallet.shared.domain

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.cash.paging.Pager
import com.easy.wallet.core.commom.Constants
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.data.repository.transactions.TRANSACTION_PAGER_LIMIT
import com.easy.wallet.shared.data.repository.transactions.TransactionPagingSource
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat

class TransactionPagerUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val supportedTokenRepository: SupportedTokenRepository,
    private val ethereumRepository: TokenRepository
) {
    operator fun invoke(tokenId: String): Flow<PagingData<Transaction>> {
        return combine(
            supportedTokenRepository.findTokenByIdFlow(tokenId),
            walletRepository.forActivatedOne()
        ) { token, wallet ->
            val hdWallet = wallet?.let { HDWallet(it.mnemonic, it.passphrase) }
                ?: throw NoSuchElementException("No wallet had been set yet.")
            val foundToken =
                token ?: throw NoSuchElementException("No token found, id is: $tokenId")
            Pair(hdWallet, foundToken)
        }.flatMapConcat { (hdWallet, token) ->
            val account = when (token.chainName) {
                Constants.ETH_CHAIN_NAME -> hdWallet.getAddressForCoin(CoinType.Ethereum)
                else -> ""
            }
            // will do: according token information choice the read token repository
            Pager(
                config = PagingConfig(pageSize = TRANSACTION_PAGER_LIMIT, prefetchDistance = 2),
                pagingSourceFactory = {
                    TransactionPagingSource(
                        token,
                        account,
                        ethereumRepository
                    )
                }
            ).flow
        }
    }
}
