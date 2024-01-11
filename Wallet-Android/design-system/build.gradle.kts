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

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")

    implementation(libs.coil.kt.compose)
}
