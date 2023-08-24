package com.easy.wallet.domain.hdwallet

import com.trustwallet.core.HDWallet

interface HDWalletInterface {
    fun hdWallet(): HDWallet
}