plugins {
    id("easy.library.multiplatform")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.lifecycle)
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

android {
    namespace = "com.easy.wallet.core"
}
dependencies {
}
