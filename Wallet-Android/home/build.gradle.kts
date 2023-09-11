@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(easy.plugins.android.feature.koin)
    alias(easy.plugins.android.library.jacoco)
}

android {
    namespace = "com.easy.wallet.home"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":Wallet-Android:core"))
    implementation(project(":shared:datastore"))
    implementation(project(":shared:data"))
    implementation(project(":shared:model"))
}