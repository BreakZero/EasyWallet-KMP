@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
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
        podfile = rootProject.file("Wallet-iOS/Podfile")
        framework {
            export(project(":platform:core"))
        }
    }
    sourceSets {
        getByName("commonMain") {
            dependencies {
                api(project(":platform:core"))
                implementation(project(":platform:model"))
                implementation(project(":platform:database"))
                implementation(project(":platform:datastore"))
                implementation(project(":platform:network"))

                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)

                api("com.trustwallet:wallet-core-kotlin:3.2.20")
                implementation("com.ionspin.kotlin:bignum:0.3.8")
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
    namespace = "com.easy.wallet.shared"
}
