package com.easy.wallet.onboard.restore

internal sealed interface RestoreWalletEvent {
    data object OnImport : RestoreWalletEvent
}
