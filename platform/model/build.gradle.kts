plugins {
    id("easy.library.multiplatform")
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCRefinement")
        }
        getByName("commonMain") {
            dependencies {
                implementation(libs.kotlinx.datetime)
            }
        }
    }
}

android {
    namespace = "com.easy.wallet.model"
}
