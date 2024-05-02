package com.easy.wallet.shared.data.multiwallet

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.easy.wallet.database.SharedDatabase
import com.easy.wallet.model.Wallet
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MultiWalletRepository internal constructor(
    sharedDatabase: SharedDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val queries = sharedDatabase.database.walletQueries

    @NativeCoroutines
    suspend fun insertOne(
        mnemonic: String,
        passphrase: String,
        onCompleted: () -> Unit = {}
    ) = withContext(dispatcher) {
        queries.transaction {
            afterCommit(onCompleted)

            val currentlyOne = queries.forActivatedOne().executeAsOneOrNull()
            currentlyOne?.let {
                queries.inActivateById(it.id)
            }
            queries.insertWallet(
                mnemonic = mnemonic,
                passphrase = passphrase,
                isActivated = true,
                createAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            )
        }
    }

    @NativeCoroutines
    fun forActivatedOne(): Flow<Wallet?> {
        return queries.forActivatedOne().asFlow().mapToOneOrNull(dispatcher).map {
            it?.let {
                Wallet(
                    mnemonic = it.mnemonic,
                    passphrase = it.passphrase,
                    isActivated = it.isActivated ?: false,
                    createAt = it.createAt,
                )
            }
        }
    }
}
