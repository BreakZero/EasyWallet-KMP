package org.easy.mobile.convention


import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = AndroidBuildConfig.compileSdkVersion
        defaultConfig.minSdk = AndroidBuildConfig.minSdkVersion

        buildFeatures {
            compose = true
        }

        dependencies {
            with(libs) {
                findLibrary("androidx-compose-bom").ifPresent {
                    add("implementation", platform(it))
                    add("androidTestImplementation", platform(it))
                }
                findLibrary("androidx.compose.ui.tooling").ifPresent {
                    add(
                        "debugImplementation",
                        it
                    )
                }
                findLibrary("junit").ifPresent { add("testImplementation", it) }

                findBundle("compose.android.bundle").ifPresent { add("implementation", it) }
            }
        }

        testOptions {
            unitTests {
                // For Robolectric
                isIncludeAndroidResources = true
            }
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
        fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
            rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        // using special config for your project
        // stabilityConfigurationFile.set(rootProject.layout.projectDirectory.file("compose_compiler_config.conf"))
    }
}
