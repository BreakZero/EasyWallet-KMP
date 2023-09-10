plugins {
    alias(easy.plugins.android.feature.koin)
    alias(easy.plugins.android.library.jacoco)
}

android {
    namespace = "com.easy.wallet.onboard"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":shared:data"))
    implementation(project(":shared:datastore"))
    implementation(project(":shared:model"))
    implementation(project(":shared:domain"))
}