package com.easy.wallet.home.transactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.shared.domain.TransactionsUseCase

private const val LIMIT = 20

internal class TransactionPagingSource(
    private val chain: String,
    private val transactionsUseCase: TransactionsUseCase
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val currentPage = params.key ?: 1
            val transactions = transactionsUseCase(
                chain = chain,
                page = currentPage,
                offset = LIMIT,
                account = "0x81080a7e991bcDdDBA8C2302A70f45d6Bd369Ab5",
            )
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
