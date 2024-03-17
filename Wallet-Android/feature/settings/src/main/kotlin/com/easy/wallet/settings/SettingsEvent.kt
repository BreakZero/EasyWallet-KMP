package com.easy.wallet.settings

internal sealed interface SettingsEvent {
    data object PopBack : SettingsEvent
    data object ClickChainManager: SettingsEvent
    data object ClickTokenManager: SettingsEvent
}
