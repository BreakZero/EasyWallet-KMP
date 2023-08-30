buildscript {
    dependencies {
        classpath(libs.kotlinx.atomicfu)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    kotlin("android").version(libs.versions.org.jetbrains.kotlin.android).apply(false)
    kotlin("multiplatform").version(libs.versions.org.jetbrains.kotlin.android).apply(false)
}
