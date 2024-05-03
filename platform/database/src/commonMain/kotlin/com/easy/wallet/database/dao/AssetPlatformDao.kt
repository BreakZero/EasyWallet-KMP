package com.easy.wallet.database.dao

interface AssetPlatformDao {
    suspend fun findAll()

    suspend fun insert()
}
