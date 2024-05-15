@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("easy.android.library")
    id("easy.android.library.compose")
}

android {
    namespace = "com.easy.wallet.android.core"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
}
