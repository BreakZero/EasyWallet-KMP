buildscript {
    dependencies {
        classpath(libs.kotlinx.atomicfu)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}
