@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(easy.plugins.android.feature.koin)
    alias(easy.plugins.android.library.jacoco)
}

android {
    namespace = "com.easy.wallet.news"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":shared:core"))
    implementation(project(":shared:data"))
    implementation(project(":shared:model"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}