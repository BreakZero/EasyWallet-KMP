package com.easy.wallet.datastore.platform

import android.content.Context

internal actual class SharedUserDefaults(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)
    actual fun getString(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    actual fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}