@file:Suppress("DSL_SCOPE_VIOLATION")
buildscript {
    dependencies {
        classpath(libs.kotlinx.atomicfu)
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.15.1")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.skie) apply false
}

subprojects {
    apply(from = "${rootProject.rootDir}/ktlint.gradle.kts")
}
