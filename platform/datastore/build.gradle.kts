plugins {
    id("easy.library.multiplatform")
}
kotlin {
    sourceSets {
        getByName("androidMain") {
            dependencies {
                implementation("androidx.security:security-crypto:1.0.0")
            }
        }
        getByName("commonMain") {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(project(":platform:core"))
                implementation(project(":platform:model"))
            }
        }
    }
}
android {
    namespace = "com.easy.wallet.datastore"
}
