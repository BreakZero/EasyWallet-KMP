@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
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
                implementation(project(":platform:core"))
                implementation(project(":platform:model"))
                implementation(project(":platform:datastore"))
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
//                implementation("co.touchlab:sqliter-driver:1.3.1")
                implementation("co.touchlab:stately-common:2.0.5")
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
