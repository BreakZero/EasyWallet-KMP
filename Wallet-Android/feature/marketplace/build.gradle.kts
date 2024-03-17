@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("org.easy.android.library.compose")
    id("org.easy.koin")
}

android {
    namespace = "com.easy.wallet.marketplace"
}

dependencies {
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":platform:shared"))
    implementation(project(":platform:network"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")
}
