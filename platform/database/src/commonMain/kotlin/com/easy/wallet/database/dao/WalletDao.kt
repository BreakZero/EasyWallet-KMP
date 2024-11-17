package com.easy.wallet.database.dao

import com.easy.wallet.model.Wallet
import kotlinx.coroutines.flow.Flow

interface WalletDao {
  suspend fun insertWithCallback(
    mnemonic: String,
    passphrase: String,
    onCompletion: () -> Unit
  )

  suspend fun findActiveOne(): Wallet?

  fun findActiveOneStream(): Flow<Wallet?>
}
