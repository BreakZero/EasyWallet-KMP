package com.easy.wallet.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.easy.wallet.database.WalletQueries
import com.easy.wallet.model.Wallet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class WalletDaoImpl(
  private val queries: WalletQueries,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WalletDao {
  override suspend fun insertWithCallback(
    mnemonic: String,
    passphrase: String,
    onCompletion: () -> Unit
  ) {
    queries.transaction {
      afterCommit(onCompletion)

      queries.findActiveWallet().executeAsOneOrNull()?.let {
        queries.inActivateById(it.id)
      }

      queries.insertWallet(
        mnemonic = mnemonic,
        passphrase = passphrase,
        isActivated = true,
        createAt = Clock.System.now().toLocalDateTime(TimeZone.UTC)
      )
    }
  }

  override suspend fun findActiveOne(): Wallet? = withContext(dispatcher) {
    queries.findActiveWallet().executeAsOneOrNull()?.let {
      Wallet(
        mnemonic = it.mnemonic,
        passphrase = it.passphrase,
        isActivated = it.isActivated ?: false,
        createAt = it.createAt
      )
    }
  }

  override fun findActiveOneStream(): Flow<Wallet?> = queries.findActiveWallet().asFlow().mapToOneOrNull(dispatcher).map {
    it?.let {
      Wallet(
        mnemonic = it.mnemonic,
        passphrase = it.passphrase,
        isActivated = it.isActivated ?: false,
        createAt = it.createAt
      )
    }
  }
}
