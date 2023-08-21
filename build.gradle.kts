plugins {
    //trick: for the same plugin versions in all sub-modules
//    id("com.android.application").version(libs.versions.androidGradlePlugin).apply(false)
//    id("com.android.library").version(libs.versions.androidGradlePlugin).apply(false)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    kotlin("android").version(libs.versions.org.jetbrains.kotlin.android).apply(false)
    kotlin("multiplatform").version(libs.versions.org.jetbrains.kotlin.android).apply(false)
}
