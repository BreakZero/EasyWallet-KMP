package com.easy.wallet.data.multiwallet

import com.easy.wallet.database.platform.DatabaseDriverFactory
import com.easy.wallet.datastore.UserPasswordStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class MultiWalletRepository(
    private val factory: DatabaseDriverFactory,
    private val userStorage: UserPasswordStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val queries = factory.createDriver(userStorage.getPassword())
}