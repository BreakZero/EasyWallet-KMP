package com.easy.wallet.datastore

import com.easy.wallet.datastore.platform.SharedUserDefaults
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserPasswordStorage: KoinComponent {
    private val userDefaults: SharedUserDefaults by inject()
    companion object {
        private const val PASSWORD_KEY = ""
    }
    fun putPassword(password: String) {
        userDefaults.putString(PASSWORD_KEY, password)
    }

    fun getPassword(): String {
        return userDefaults.getString(PASSWORD_KEY)
    }
}