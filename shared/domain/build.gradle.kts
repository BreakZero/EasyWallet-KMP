plugins {
    id("easy.multiplatform.library")
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(project(":shared:model"))
                implementation(project(":shared:core"))
                implementation(project(":shared:data"))
                implementation(libs.coroutine.core)
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.domain"
}
