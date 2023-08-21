plugins {
    id("easy.multiplatform.library")
    id("app.cash.sqldelight") version libs.versions.sqldelight
}

kotlin {
    cocoapods {
        pod("SQLCipher", "~> 4.0")
    }
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(libs.kotlinx.datetime)
                api(libs.sqldelight.coroutines)
                implementation(project(":shared:core"))
                implementation(project(":shared:model"))
            }
        }
        getByName("androidMain") {
            dependencies {
                implementation("net.zetetic:android-database-sqlcipher:4.5.4@aar")
                implementation(libs.sqldelight.android)
            }
        }
        getByName("iosMain") {
            dependencies {
                implementation(libs.sqldelight.native)
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.database"
}

sqldelight {
    databases {
        create("WalletDatabase") {
            packageName.set("com.easy.wallet.database")
        }
    }
    linkSqlite.set(true)
}
