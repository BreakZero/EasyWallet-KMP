package org.easy.mobile.convention.plugins

import org.easy.mobile.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                libs.findBundle("koin.android.bundle").ifPresent {
                    add("implementation", it)
                }
            }
        }
    }
}
