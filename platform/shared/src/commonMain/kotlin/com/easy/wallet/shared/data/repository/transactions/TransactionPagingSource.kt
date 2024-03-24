package com.easy.wallet.shared.data.repository.transactions

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.shared.data.repository.TokenRepository

private const val LIMIT = 20

internal class TransactionPagingSource(
    private val tokenInformation: TokenInformation,
    private val account: String,
    private val tokenRepository: TokenRepository
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val currPage = params.key ?: 1
            val transactions = tokenRepository.loadTransactions(
                account,
                tokenInformation,
                currPage,
                LIMIT
            )
            LoadResult.Page(
                data = transactions,
                prevKey = if (currPage == 1) null else currPage - 1,
                nextKey = if (transactions.isEmpty()) null else currPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
