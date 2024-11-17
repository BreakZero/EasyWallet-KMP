package com.easy.wallet.datastore

import com.easy.wallet.datastore.platform.SharedUserDefaults
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseKeyStorage : KoinComponent {
  private val userDefaults: SharedUserDefaults by inject()

  companion object {
    private const val DATABASE_KEY = "database_key"
  }

  fun init(randomKey: String) {
    if (databaseKey().isBlank() && randomKey.isNotBlank()) {
      userDefaults.putString(DATABASE_KEY, randomKey)
    }
  }

  fun databaseKey(): String = userDefaults.getString(DATABASE_KEY)
}
