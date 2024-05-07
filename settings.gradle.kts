pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(uri("https://maven.pkg.github.com/trustwallet/wallet-core")) {
            credentials {
                with(tokenProperty()) {
                    username = getProperty("gpr.name") ?: System.getenv("GT_USERNAME")
                    password = getProperty("gpr.key") ?: System.getenv("GT_TOKEN")
                }
            }
        }
    }
    versionCatalogs {
        create("easy") {
            from(files("./build-logic/building.versions.toml"))
        }
    }
}

fun tokenProperty(): java.util.Properties {
    val properties = java.util.Properties()
    val localProperties = File(rootDir, "github_token.properties")

    if (localProperties.isFile) {
        java.io.InputStreamReader(
            java.io.FileInputStream(localProperties)
        ).use { reader ->
            properties.load(reader)
        }
    }
    return properties
}

rootProject.name = "EasyWallet"
include(":platform:core")
include(":platform:database")
include(":platform:model")
include(":platform:shared")
include(":platform:datastore")
include(":platform:network")
include(":platform:shared")

include(":Wallet-Android:app")
include(":Wallet-Android:feature:onboard")
include(":Wallet-Android:design-system")
include(":Wallet-Android:feature:discover")
include(":Wallet-Android:feature:home")
include(":Wallet-Android:feature:marketplace")
include(":Wallet-Android:feature:news")
include(":Wallet-Android:feature:settings")
include(":Wallet-Android:core")
include(":Wallet-Android:feature:asset_manager")
include(":Wallet-Android:feature:send")
