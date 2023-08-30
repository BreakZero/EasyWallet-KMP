package com.easy.wallet.domain.hdwallet

import com.easy.wallet.data.multiwallet.MultiWalletRepository
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CreateWalletUseCase(
    private val multiWalletRepository: MultiWalletRepository
) {
    suspend operator fun invoke(passphrase: String = ""): String {
        val hdWallet = HDWallet(256, passphrase)
        withContext(Dispatchers.IO) {
            multiWalletRepository.insertOne(hdWallet.mnemonic, passphrase)
        }
        return hdWallet.mnemonic
    }
}