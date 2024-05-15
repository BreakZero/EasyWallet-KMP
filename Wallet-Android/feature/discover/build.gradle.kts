@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("easy.android.library")
    id("easy.android.library.compose")
    id("easy.android.koin")
}

android {
    namespace = "com.easy.wallet.discover"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":platform:core"))
    implementation(project(":platform:shared"))
    implementation(project(":platform:model"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
