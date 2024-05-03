package com.easy.wallet.database.dao

import com.easy.wallet.database.CoinEntityQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class CoinDaoImpl(
    private val queries: CoinEntityQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): CoinDao {
}
