import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuspendInterop
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("org.easy.multiplatform")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.nativecoroutines)
    alias(libs.plugins.skie)
}

kotlin {
    cocoapods {
        dependencies {
            pod("TrustWalletCore", moduleName = "WalletCore")
        }
        podfile = rootProject.file("Wallet-iOS/Podfile")
        framework {
            export(project(":platform:core"))
            isStatic = true

            listOf(
                iosX64(),
                iosArm64(),
                iosSimulatorArm64()
            ).forEach {
                it.binaries.all {
                    linkerOpts += "-ld64"
                }
            }
            embedBitcode(BitcodeEmbeddingMode.DISABLE)
        }
    }
    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain {
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

                implementation("app.cash.paging:paging-common:3.3.0-alpha02-0.5.1")
                implementation(libs.skie.annotations)

                api("com.trustwallet:wallet-core-kotlin:4.0.31")
                implementation("com.ionspin.kotlin:bignum:0.3.9")
                implementation("co.touchlab:kermit:2.0.3")
            }
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            api("com.trustwallet:wallet-core-kotlin:4.0.31")
        }

        iosMain {
            dependencies {
                implementation("app.cash.paging:paging-runtime-uikit:3.3.0-alpha02-0.5.1")
                implementation(libs.ktor.client.darwin)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.paging)
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.shared"
}

skie {
    features {
        group {
            SealedInterop.Enabled(true)
            EnumInterop.Enabled(true)
            coroutinesInterop.set(false)
            SuspendInterop.Enabled(false)
            FlowInterop.Enabled(false)
        }
    }
}
