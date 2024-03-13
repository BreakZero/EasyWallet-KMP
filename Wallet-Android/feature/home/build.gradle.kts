@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("org.easy.android.library.compose")
    id("org.easy.koin")
}

android {
    namespace = "com.easy.wallet.home"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":Wallet-Android:core"))
    implementation(project(":platform:datastore"))
    implementation(project(":platform:shared"))
    implementation(project(":platform:model"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
}
