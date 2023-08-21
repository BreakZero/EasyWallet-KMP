/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.AbstractAppExtension
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.easy.configs.configureFlavors
import com.easy.configs.configureKotlinAndroid
import com.easy.configs.configurePrintApksTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    versionCode = AndroidBuildConfig.versionCode
                    versionName = AndroidBuildConfig.versionName
                    targetSdk = AndroidBuildConfig.targetSdkVersion
                }
                configureKotlinAndroid(this)
                configureFlavors(this)
            }

            // rename output file
            /*extensions.configure<AbstractAppExtension> {
                applicationVariants.all {
                    this.outputs.all {
                        val outputFileName = "${applicationId}-v${versionName}(${this.versionCode})-${name}.${outputFile.extension}"
                        (this as BaseVariantOutputImpl).outputFileName = outputFileName
                    }
                }
            }*/
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }
        }
    }
}
