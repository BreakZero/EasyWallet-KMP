plugins {
    id("easy.multiplatform.library")
}

kotlin {
    cocoapods {
        dependencies {
            pod("TrustWalletCore", moduleName = "WalletCore")
        }
        podfile = project.file("../../Wallet-iOS/Podfile")
    }
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(project(":shared:model"))
                implementation(project(":shared:core"))
                implementation(project(":shared:data"))
                implementation(libs.coroutine.core)

                api("com.trustwallet:wallet-core-kotlin:3.2.13")
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.domain"
}
