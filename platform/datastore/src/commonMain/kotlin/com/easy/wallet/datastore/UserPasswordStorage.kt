package com.easy.wallet.datastore

import com.easy.wallet.datastore.platform.SharedUserDefaults
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserPasswordStorage: KoinComponent {
    private val userDefaults: SharedUserDefaults by inject()

    companion object {
        private const val PASSWORD_KEY = "password_key"
        private const val KEY_BIOMETRIC_ENABLED = ""
    }

    suspend fun putPassword(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        password: String
    ) = withContext(dispatcher) {
        userDefaults.putString(PASSWORD_KEY, password)
    }

    fun syncPassword(): String {
        return userDefaults.getString(PASSWORD_KEY)
    }

    fun passwordStream(): Flow<String> {
        return flow {
            emit(userDefaults.getString(PASSWORD_KEY))
        }
    }
}
