plugins {
    id("easy.multiplatform.library")
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(project(":shared:core"))
                implementation(project(":shared:model"))
                implementation(project(":shared:database"))
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.data"
}
