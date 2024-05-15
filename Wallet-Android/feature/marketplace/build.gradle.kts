@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("easy.android.library")
    id("easy.android.library.compose")
    id("easy.android.koin")
}

android {
    namespace = "com.easy.wallet.marketplace"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":platform:model"))
    implementation(project(":platform:shared"))
    implementation(project(":platform:network"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")
}
