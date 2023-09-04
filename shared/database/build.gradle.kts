plugins {
    id("easy.multiplatform.library")
    id("app.cash.sqldelight") version libs.versions.sqldelight
    id("kotlinx-atomicfu")
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(libs.kotlinx.datetime)
                api(libs.sqldelight.coroutines)
                implementation(project(":shared:core"))
                implementation(project(":shared:model"))
                implementation(project(":shared:datastore"))
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
//                implementation("co.touchlab:sqliter-driver:1.2.3")
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
            version = 1
            packageName.set("com.easy.wallet.database")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
        }
    }
    linkSqlite.set(true)
}
