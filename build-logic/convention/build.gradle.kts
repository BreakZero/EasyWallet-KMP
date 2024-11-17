
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(easy.android.tools.build)
    compileOnly(easy.android.tools.common)
    compileOnly(easy.compose.gradle.plugin)
    compileOnly(easy.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins.register("androidApplication") {
        id = "easy.android.application"
        implementationClass = "org.easy.mobile.convention.plugins.AndroidApplicationConventionPlugin"
    }
    plugins.register("androidApplicationCompose") {
        id = "easy.android.application.compose"
        implementationClass = "org.easy.mobile.convention.plugins.AndroidApplicationComposeConventionPlugin"
    }
    plugins.register("androidLibrary") {
        id = "easy.android.library"
        implementationClass = "org.easy.mobile.convention.plugins.AndroidLibraryConventionPlugin"
    }
    plugins.register("androidLibraryCompose") {
        id = "easy.android.library.compose"
        implementationClass = "org.easy.mobile.convention.plugins.AndroidLibraryComposeConventionPlugin"
    }
    plugins.register("androidLint") {
        id = "easy.android.lint"
        implementationClass = "org.easy.mobile.convention.plugins.AndroidLintConventionPlugin"
    }
    plugins.register("androidTest") {
        id = "easy.android.test"
        implementationClass = "org.easy.mobile.convention.plugins.AndroidTestConventionPlugin"
    }
    plugins.register("androidHilt") {
        id = "easy.android.hilt"
        implementationClass = "org.easy.mobile.convention.plugins.HiltConventionPlugin"
    }
    plugins.register("androidJacoco") {
        id = "easy.android.jacoco"
        implementationClass = "org.easy.mobile.convention.plugins.JacocoConventionPlugin"
    }
    plugins.register("jvmLibrary") {
        id = "easy.library.jvm"
        implementationClass = "org.easy.mobile.convention.plugins.JvmLibraryConventionPlugin"
    }
    plugins.register("androidKoin") {
        id = "easy.android.koin"
        implementationClass = "org.easy.mobile.convention.plugins.KoinConventionPlugin"
    }
    plugins.register("multiplatformLibrary") {
        id = "easy.library.multiplatform"
        implementationClass = "org.easy.mobile.convention.plugins.MultiplatformCommonConventionPlugin"
    }
}
