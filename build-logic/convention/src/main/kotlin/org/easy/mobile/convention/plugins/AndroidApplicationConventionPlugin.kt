package org.easy.mobile.convention.plugins

import com.android.build.api.dsl.ApplicationExtension
import org.easy.mobile.convention.AndroidBuildConfig
import org.easy.mobile.convention.configureFlavors
import org.easy.mobile.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("easy.android.lint")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AndroidBuildConfig.targetSdkVersion
                @Suppress("UnstableApiUsage")
                testOptions.animationsDisabled = true
            }
        }
    }
}