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
include(":Wallet-Android:design-system")
include(":Wallet-Android:onboard")

include(":shared:core")
include(":shared:database")
include(":shared:domain")
include(":shared:model")
include(":shared:data")
include(":Wallet-Android:discover")
include(":Wallet-Android:home")
include(":Wallet-Android:marketplace")
