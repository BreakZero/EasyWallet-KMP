package com.easy.wallet.database.dao

import com.easy.wallet.database.AssetPlatformEntityQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class AssetPlatformDaoImpl(
    private val queries: AssetPlatformEntityQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AssetPlatformDao {
    override suspend fun findAll() {
        queries.findAll().executeAsOne()
        TODO("Not yet implemented")
    }

    override suspend fun insert() {
        TODO("Not yet implemented")
    }
}
