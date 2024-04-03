package com.easy.wallet

import android.app.Application
import com.easy.wallet.di.appModule
import com.easy.wallet.discover.di.discoverModule
import com.easy.wallet.home.di.homeModule
import com.easy.wallet.marketplace.di.marketModule
import com.easy.wallet.news.di.newsModule
import com.easy.wallet.onboard.di.onboardModule
import com.easy.wallet.send.di.sendModule
import com.easy.wallet.settings.di.settingsModule
import com.easy.wallet.shared.di.initKoin
import com.easy.wallet.shared.di.sharedModule
import com.easy.wallet.token_manager.di.assetModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WalletApplication : Application() {
    init {
        System.loadLibrary("TrustWalletCore")
    }

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(applicationContext)
//            modules(appModule)
//            modules(homeModule)
//            modules(newsModule)
//            modules(discoverModule)
//            modules(onboardModule)
//            modules(sharedModule)
//            modules(marketModule)
//            modules(settingsModule)
//            modules(assetModule())
//        }

        initKoin {
            androidContext(this@WalletApplication)
            modules(appModule)
            modules(homeModule)
            modules(newsModule)
            modules(discoverModule)
            modules(onboardModule)
            modules(marketModule)
            modules(settingsModule)
            modules(assetModule())
            modules(sendModule)
        }
    }
}
