plugins {
    alias(easy.plugins.android.feature.koin)
}

android {
    namespace = "com.easy.wallet.design"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.compose.activity)
    implementation(libs.bundles.compose.android.bundle)

    implementation(libs.coil.kt.compose)
}