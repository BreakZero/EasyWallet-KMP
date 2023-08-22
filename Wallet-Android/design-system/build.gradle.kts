plugins {
    id("easy.android.library")
    id("easy.android.library.compose")
    id("easy.android.library.jacoco")
}

android {
    namespace = "com.easy.wallet.design"
}

dependencies {
    implementation(libs.core.ktx)

    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.window.size)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.icons.core)
    // Optional - Add full set of material icons
    implementation(libs.androidx.compose.material.icons.extended)
}