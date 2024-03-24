package com.easy.wallet.shared.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.transactions.TransactionRepository
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.firstOrNull

class TransactionsUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val ethereumTransactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        token: TokenInformation
    ): Pager<Int, Transaction> {
        val hdWallet = walletRepository.forActivatedOne().firstOrNull()?.let {
            HDWallet(it.mnemonic, it.passphrase)
        } ?: throw IllegalArgumentException("No wallet had been set yet.")
        val account = when (token.chainName) {
            "Ethereum" -> hdWallet.getAddressForCoin(CoinType.Ethereum)
            else -> ""
        }
        return Pager(
            config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
            pagingSourceFactory = {
                TransactionPagingSource(
                    token,
                    account, // 0x81080a7e991bcDdDBA8C2302A70f45d6Bd369Ab5
                    ethereumTransactionRepository
                )
            }
        )
    }
}

private const val LIMIT = 20

internal class TransactionPagingSource(
    private val token: TokenInformation,
    private val account: String,
    private val transactionRepository: TransactionRepository
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val currentPage = params.key ?: 1
            val transactions = if (token.contract.isNullOrBlank()) {
                transactionRepository.getTransactions(token, currentPage, LIMIT, account)
            } else {
                transactionRepository.getContractInternalTransactions(token, currentPage, LIMIT, account)
            }

            LoadResult.Page(
                data = transactions,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (transactions.isEmpty()) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
