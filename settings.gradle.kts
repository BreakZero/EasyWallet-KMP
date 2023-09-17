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
                    username = getProperty("gpr.name") ?: System.getenv("GITHUB_USERNAME").also {
                        println("========= $it")
                    }
                    password = getProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN").also {
                        println("========= $it")
                    }
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

rootProject.name = "E-Wallet"
include(":shared:core")
include(":shared:database")
include(":shared:domain")
include(":shared:model")
include(":shared:data")
include(":shared:datastore")

include(":Wallet-Android:app")
include(":Wallet-Android:onboard")
include(":Wallet-Android:design-system")
include(":Wallet-Android:discover")
include(":Wallet-Android:home")
include(":Wallet-Android:marketplace")
include(":Wallet-Android:news")
include(":Wallet-Android:settings")
include(":Wallet-Android:core")
