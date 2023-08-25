plugins {
    id("easy.multiplatform.library")
}
kotlin {
    sourceSets {
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