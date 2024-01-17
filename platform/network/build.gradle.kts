import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("easy.multiplatform.library")
    alias(libs.plugins.kotlin.serialization)
    id("com.codingfeline.buildkonfig")
}

buildkonfig {
    packageName = "com.easy.wallet.network.key"
    val apiKeys = apiKeyProperties()
    defaultConfigs {
        buildConfigField(STRING, "ETHERSCAN_KEY", "${apiKeys["etherscan"]}")
        buildConfigField(STRING, "COINGECKO_KEY", "${apiKeys["coingecko"]}")
        buildConfigField(STRING, "OPENSEA_KEY", "${apiKeys["opensea"]}")
    }
}

fun apiKeyProperties(): Properties {
    val properties = Properties()
    val keyProperties = rootProject.file("keystore/apikey.properties")

    if (keyProperties.isFile) {
        InputStreamReader(FileInputStream(keyProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    }
    return properties
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                api(project(":platform:core"))
                implementation(project(":platform:model"))

                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.websockets)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }
        getByName("iosMain") {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        getByName("androidMain") {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.network"
}
