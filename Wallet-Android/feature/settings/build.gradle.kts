@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("easy.android.library")
    id("easy.android.library.compose")
    id("easy.android.koin")
}

android {
    namespace = "com.easy.wallet.settings"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":Wallet-Android:core"))
}
