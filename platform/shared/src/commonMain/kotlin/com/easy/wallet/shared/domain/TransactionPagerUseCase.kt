package com.easy.wallet.shared.domain

import androidx.paging.PagingConfig
import app.cash.paging.Pager
import com.easy.wallet.core.commom.Constants
import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.data.repository.transactions.TransactionPagingSource
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.firstOrNull

class TransactionPagerUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val ethereumRepository: TokenRepository
) {
    suspend operator fun invoke(tokenInformation: TokenInformation): Pager<Int, Transaction> {
        val hdWallet = walletRepository.forActivatedOne().firstOrNull()?.let {
            HDWallet(it.mnemonic, it.passphrase)
        } ?: throw IllegalArgumentException("No wallet had been set yet.")
        val account = when (tokenInformation.chainName) {
            Constants.ETH_CHAIN_NAME -> hdWallet.getAddressForCoin(CoinType.Ethereum)
            else -> ""
        }
        // will do: according token information choice the read token repository
        return Pager(
            config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
            pagingSourceFactory = {
                TransactionPagingSource(
                    tokenInformation,
                    account,
                    ethereumRepository
                )
            }
        )
    }
}
