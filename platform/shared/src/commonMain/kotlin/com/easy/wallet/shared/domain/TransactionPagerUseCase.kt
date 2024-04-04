package com.easy.wallet.shared.domain

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.cash.paging.Pager
import com.easy.wallet.shared.data.repository.transactions.TRANSACTION_PAGER_LIMIT
import com.easy.wallet.shared.data.repository.transactions.TransactionPagingSource
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionPagerUseCase internal constructor(
    private val getToKenBasicInfoUseCase: GetToKenBasicInfoUseCase,
    private val getExactTokenRepositoryUseCase: GetExactTokenRepositoryUseCase
) {
    operator fun invoke(tokenId: String): Flow<PagingData<TransactionUiModel>> {
        return getToKenBasicInfoUseCase(tokenId).flatMapConcat { tokenInfo ->
            val exactRepository = getExactTokenRepositoryUseCase(tokenInfo)
            Pager(
                config = PagingConfig(pageSize = TRANSACTION_PAGER_LIMIT, prefetchDistance = 2),
                pagingSourceFactory = {
                    TransactionPagingSource(
                        tokenInfo,
                        exactRepository
                    )
                }
            ).flow
        }
    }
}
