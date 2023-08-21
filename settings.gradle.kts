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
    }
}

rootProject.name = "E-Wallet"
include(":Wallet-Android:app")

include(":shared:core")
include(":Wallet-Android:design-system")
include(":shared:database")
include(":shared:domain")
include(":shared:model")
include(":shared:data")
