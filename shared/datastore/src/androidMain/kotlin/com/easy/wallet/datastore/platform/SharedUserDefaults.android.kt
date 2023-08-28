package com.easy.wallet.datastore.platform

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


internal actual class SharedUserDefaults(private val context: Context) {
    companion object {
        private const val FILENAME = "user_default_prefs"
    }

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        FILENAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual fun getString(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    actual fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}