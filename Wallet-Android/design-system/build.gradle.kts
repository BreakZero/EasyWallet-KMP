@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(easy.plugins.android.library.compose)
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