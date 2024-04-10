package com.easy.wallet.shared.data.repository.transactions

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.model.transaction.TransactionUiModel

internal const val TRANSACTION_PAGER_LIMIT = 20

internal class TransactionPagingSource(
    private val tokenInfo: TokenBasicResult,
    private val tokenRepository: TokenRepository
) : PagingSource<Int, TransactionUiModel>() {
    override fun getRefreshKey(state: PagingState<Int, TransactionUiModel>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionUiModel> {
        return try {
            val currPage = params.key ?: 1
            val transactions = tokenRepository.loadTransactions(
                tokenInfo,
                currPage,
                TRANSACTION_PAGER_LIMIT
            )
            LoadResult.Page(
                data = transactions,
                prevKey = if (currPage <= 1) null else currPage - 1,
                nextKey = if (transactions.isEmpty()) null else currPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
