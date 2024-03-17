@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("org.easy.android.library.compose")
}

android {
    namespace = "com.easy.wallet.android.core"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
