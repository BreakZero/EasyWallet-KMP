package org.easy.mobile.convention.plugins

import org.easy.mobile.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }
            dependencies {
                with(libs) {
                    findLibrary("hilt.android").ifPresent { add("implementation", it) }
                    findLibrary("hilt.compiler").ifPresent { add("ksp", it) }
                }
            }
        }
    }
}