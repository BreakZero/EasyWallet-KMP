package com.easy.wallet.datastore.platform

import platform.Foundation.NSUserDefaults

internal actual class SharedUserDefaults {
  private val userDefaults = NSUserDefaults.standardUserDefaults

  actual fun getString(key: String): String = userDefaults.stringForKey(key) ?: ""

  actual fun putString(key: String, value: String) {
    userDefaults.setObject(value, key)
  }
}
