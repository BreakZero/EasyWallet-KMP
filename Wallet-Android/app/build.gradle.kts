import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    id("easy.android.application")
    id("easy.android.application.compose")
    id("easy.android.application.jacoco")
    id("jacoco")
}

android {
    namespace = "com.easy.wallet"
    val keyProperties = keyStoreProperties()
    signingConfigs {
        create("release") {
            storeFile = rootProject.file(keyProperties.getProperty("storeFile"))
            storePassword = keyProperties.getProperty("storePassword")
            keyAlias = keyProperties.getProperty("keyAlias")
            keyPassword = keyProperties.getProperty("keyPassword")
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    defaultConfig {
        applicationId = "com.easy.wallet"
    }
}

fun keyStoreProperties(): Properties {
    val properties = Properties()
    val keyProperties = rootProject.file("keystore/keystore.properties")

    if (keyProperties.isFile) {
        InputStreamReader(FileInputStream(keyProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    }
    return properties
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":shared:data"))
    implementation(project(":shared:model"))
    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":Wallet-Android:home"))
    implementation(project(":Wallet-Android:marketplace"))
    implementation(project(":Wallet-Android:discover"))
}
