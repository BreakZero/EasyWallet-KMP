plugins {
    id("easy.multiplatform.library")
    alias(libs.plugins.kotlin.serialization)
    id("kotlinx-atomicfu")
}

kotlin {
    cocoapods {
        dependencies {
            pod("TrustWalletCore", moduleName = "WalletCore")
        }
        framework {
            export(project(":shared:core"))
        }
    }
    sourceSets {
        getByName("commonMain") {
            dependencies {
                api(project(":shared:core"))
                implementation(project(":shared:model"))
                implementation(project(":shared:database"))
                implementation(project(":shared:datastore"))

                implementation(libs.kotlinx.datetime)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)

                api("com.trustwallet:wallet-core-kotlin:3.2.18")
            }
        }
        getByName("iosMain") {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        getByName("androidMain") {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.data"
}
