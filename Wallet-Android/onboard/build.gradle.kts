@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("easy.android.feature")
    id("easy.android.library.compose")
    id("easy.android.library.jacoco")
}

android {
    namespace = "com.easy.wallet.onboard"
}

