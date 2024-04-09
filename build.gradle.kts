@file:Suppress("DSL_SCOPE_VIOLATION")
buildscript {
    dependencies {
        classpath(libs.buildkonfig.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.skie) apply false
    alias(libs.plugins.nativecoroutines) apply false
}

subprojects {
    apply(from = "${rootProject.rootDir}/ktlint.gradle.kts")
}
