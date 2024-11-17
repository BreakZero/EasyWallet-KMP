package org.easy.mobile.convention.plugins

import com.android.build.gradle.LibraryExtension
import org.easy.mobile.convention.AndroidBuildConfig
import org.easy.mobile.convention.configureKotlinAndroid
import org.easy.mobile.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode

class MultiplatformCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.native.cocoapods")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AndroidBuildConfig.targetSdkVersion
                testOptions.animationsDisabled = true
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()

                iosX64()
                iosArm64()
                iosSimulatorArm64()
                applyDefaultHierarchyTemplate()

                sourceSets.commonMain {
                    dependencies {
                        libs.findLibrary("koin.core").ifPresent {
                            implementation(it)
                        }
                    }
                }

                (this as ExtensionAware).extensions.configure<CocoapodsExtension> {
                    configureKotlinCocoapods(this)
                }
            }
        }
    }
}

internal fun Project.configureKotlinCocoapods(
    extension: CocoapodsExtension
) = extension.apply {
    val moduleName = this@configureKotlinCocoapods.path.split(":").drop(1).joinToString("-")
    summary = "Some description for the Shared Module" // TODO replace to your feature summary
    homepage = "Link to the Shared Module homepage"
    version = "1.0"
    ios.deploymentTarget = "16.0" //your iOS deployment target
    name = moduleName
    framework {
        baseName = moduleName
        embedBitcode(BitcodeEmbeddingMode.BITCODE)
    }
}
