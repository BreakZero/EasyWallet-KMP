package com.easy.wallet

import androidx.lifecycle.ViewModel
import com.easy.wallet.datastore.DatabaseKeyStorage
import java.util.UUID

class MainViewModel(
    private val databaseKeyStorage: DatabaseKeyStorage
) : ViewModel()