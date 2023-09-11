@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(easy.plugins.android.feature.koin)
    alias(easy.plugins.android.library.jacoco)
}

android {
    namespace = "com.easy.wallet.marketplace"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":shared:data"))
}
