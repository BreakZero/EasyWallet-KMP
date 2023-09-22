package com.easy.wallet.datastore.platform

internal expect class SharedUserDefaults {
    fun getString(key: String): String
    fun putString(key: String, value: String)
}
