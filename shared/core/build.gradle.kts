plugins {
    id("easy.multiplatform.library")
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.core"
}
dependencies {
}
