package com.easy.wallet

import androidx.lifecycle.ViewModel
import com.easy.wallet.datastore.DatabaseKeyStorage

class MainViewModel(
    private val databaseKeyStorage: DatabaseKeyStorage
) : ViewModel()
