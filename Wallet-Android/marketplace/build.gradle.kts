plugins {
    id("easy.android.feature")
    id("easy.android.library.compose")
    id("easy.android.library.jacoco")
}

android {
    namespace = "com.easy.wallet.marketplace"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":shared:data"))
}
