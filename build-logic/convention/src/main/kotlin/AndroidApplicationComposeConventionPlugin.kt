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
import com.easy.configs.configureAndroidCompose
import com.easy.configs.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
            dependencies {
                val bom = libs.findLibrary("androidx-compose-bom").get()

                add("coreLibraryDesugaring", libs.findLibrary("desugar.jdk.libs").get())
                add("implementation", libs.findLibrary("core.ktx").get())
                add("implementation", libs.findLibrary("androidx.activity.compose").get())

                add("implementation", platform(bom))
                add("implementation", libs.findLibrary("androidx.compose.material3").get())
                add("implementation", libs.findLibrary("androidx.compose.material3.window.size").get())
                add("implementation", libs.findLibrary("androidx.compose.ui").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx.compose.foundation").get())
                add("implementation", libs.findLibrary("androidx.compose.material.icons.core").get())
                add("implementation", libs.findLibrary("androidx.compose.material.icons.extended").get())


                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose").get())
                add("implementation", libs.findLibrary("androidx.core.splashscreen").get())

                add("implementation", libs.findLibrary("koin.core").get())
                add("implementation", libs.findLibrary("koin.android").get())
                add("implementation", libs.findLibrary("koin.compose").get())

                add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())

                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.test.ext.junit").get())
                add("androidTestImplementation", libs.findLibrary("espresso.core").get())
                add("androidTestImplementation", platform(bom))
                add("androidTestImplementation", libs.findLibrary("ui.test.junit4").get())
            }
        }
    }
}
