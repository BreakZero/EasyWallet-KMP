@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("easy.android.library")
    id("easy.android.library.compose")
    id("easy.android.koin")
}

android {
    namespace = "com.easy.wallet.assetmanager"
}

dependencies {

    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":Wallet-Android:core"))
    implementation(project(":platform:shared"))
    implementation(project(":platform:datastore"))
    implementation(project(":platform:model"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
