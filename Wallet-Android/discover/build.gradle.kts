@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("easy.android.feature")
    id("easy.android.library.compose")
    id("easy.android.library.jacoco")
}

android {
    namespace = "com.easy.wallet.discover"
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":shared:data"))
    implementation(project(":shared:model"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
