@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(easy.plugins.android.feature.koin)
    alias(easy.plugins.android.library.jacoco)
}

android {
    namespace = "com.easy.wallet.onboard"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":Wallet-Android:core"))
    implementation(project(":platform:shared"))
    implementation(project(":platform:datastore"))
    implementation(project(":platform:model"))
}
