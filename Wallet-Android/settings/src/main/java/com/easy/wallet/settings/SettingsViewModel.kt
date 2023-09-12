package com.easy.wallet.settings

import com.easy.wallet.android.core.BaseViewModel

internal class SettingsViewModel: BaseViewModel<SettingsEvent>() {

    override fun handleEvent(event: SettingsEvent) {
        dispatchEvent(event)
    }
}