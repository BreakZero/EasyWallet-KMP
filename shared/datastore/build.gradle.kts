plugins {
    id("easy.multiplatform.library")
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
                implementation(libs.coroutine.core)
                implementation(project(":shared:core"))
                implementation(project(":shared:model"))
            }
        }
    }
}
android {
    namespace = "com.easy.wallet.datastore"
}