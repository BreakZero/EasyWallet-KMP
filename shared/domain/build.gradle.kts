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
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.domain"
}
