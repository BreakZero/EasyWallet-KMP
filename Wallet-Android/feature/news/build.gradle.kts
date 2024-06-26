@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("easy.android.library")
    id("easy.android.library.compose")
    id("easy.android.koin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.easy.wallet.news"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":Wallet-Android:core"))
    implementation(project(":platform:core"))
    implementation(project(":platform:shared"))
    implementation(project(":platform:model"))

    implementation("androidx.browser:browser:1.8.0")
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.compose.navigation)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
