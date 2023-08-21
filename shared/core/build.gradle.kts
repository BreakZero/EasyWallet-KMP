plugins {
    id("easy.multiplatform.library")
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.coroutine.core)
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.core"
}
dependencies {
    implementation(libs.androidx.annotation.jvm)
}
