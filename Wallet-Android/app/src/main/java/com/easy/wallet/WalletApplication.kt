package com.easy.wallet

import android.app.Application
import com.easy.wallet.data.di.dataModule
import com.easy.wallet.di.appModule
import com.easy.wallet.discover.di.discoverModule
import com.easy.wallet.home.di.homeModule
import com.easy.wallet.marketplace.di.marketModule
import com.easy.wallet.news.di.newsModule
import com.easy.wallet.onboard.di.onboardModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WalletApplication: Application() {
    init {
        System.loadLibrary("TrustWalletCore")
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
            modules(homeModule)
            modules(newsModule)
            modules(discoverModule)
            modules(onboardModule)
            modules(dataModule)
            modules(marketModule)
        }
    }
}