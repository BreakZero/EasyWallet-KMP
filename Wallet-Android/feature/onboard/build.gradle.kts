@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("easy.android.library")
    id("easy.android.library.compose")
    id("easy.android.koin")
    alias(libs.plugins.kotlin.serialization)
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

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.compose.navigation)
}
