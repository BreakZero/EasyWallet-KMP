plugins {
    alias(easy.plugins.android.feature.koin)
    alias(easy.plugins.android.library.jacoco)
}

android {
    namespace = "com.easy.wallet.settings"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
}
