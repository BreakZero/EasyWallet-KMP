import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(easy.plugins.android.application.compose)
    alias(easy.plugins.android.application.jacoco)
    id("jacoco")
}

android {
    defaultConfig {
        applicationId = "com.easy.wallet"
        versionCode = 1000000
        versionName = "v1.0.0"
    }
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
    packaging {
        resources {
            excludes.add("META-INF/versions/9/previous-compilation-data.bin")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.material3.window.size)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.viewmodel)
    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.bundles.koin.android.bundle)

    implementation(project(":shared:core"))
    implementation(project(":shared:data"))
    implementation(project(":shared:datastore"))
    implementation(project(":shared:model"))

    implementation(project(":Wallet-Android:design-system"))
    implementation(project(":Wallet-Android:home"))
    implementation(project(":Wallet-Android:news"))
    implementation(project(":Wallet-Android:onboard"))
    implementation(project(":Wallet-Android:marketplace"))
    implementation(project(":Wallet-Android:discover"))
    implementation(project(":Wallet-Android:settings"))
}
