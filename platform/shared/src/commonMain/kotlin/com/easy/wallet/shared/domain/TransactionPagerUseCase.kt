package com.easy.wallet.shared.domain

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.cash.paging.Pager
import com.easy.wallet.shared.data.repository.transactions.TRANSACTION_PAGER_LIMIT
import com.easy.wallet.shared.data.repository.transactions.TransactionPagingSource
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionPagerUseCase internal constructor(
    private val getAssetCoinInfoUseCase: GetAssetCoinInfoUseCase,
    private val getChainRepositoryUseCase: GetChainRepositoryUseCase
) {
    @NativeCoroutines
    operator fun invoke(coinId: String): Flow<PagingData<TransactionUiModel>> {
        return getAssetCoinInfoUseCase(coinId).flatMapConcat { assetCoin ->
            val chainRepository = getChainRepositoryUseCase(assetCoin.platform)
            Pager(
                config = PagingConfig(pageSize = TRANSACTION_PAGER_LIMIT, prefetchDistance = 2),
                pagingSourceFactory = {
                    TransactionPagingSource(
                        assetCoin,
                        chainRepository
                    )
                }
            ).flow
        }
    }
}
