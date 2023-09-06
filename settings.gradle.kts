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
                    username = getProperty("gpr.name")
                    password = getProperty("gpr.key")
                }
            }
        }
    }
//    versionCatalogs {
//        create("libs") {
//            from(files("./build-logic/libs.versions.toml"))
//        }
//    }
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

rootProject.name = "E-Wallet"
include(":Wallet-Android:app")
include(":Wallet-Android:design-system")
include(":Wallet-Android:discover")
include(":Wallet-Android:home")
include(":Wallet-Android:marketplace")

include(":shared:core")
include(":shared:database")
include(":shared:domain")
include(":shared:model")
include(":shared:data")
include(":Wallet-Android:onboard")
include(":shared:datastore")
include(":Wallet-Android:news")
