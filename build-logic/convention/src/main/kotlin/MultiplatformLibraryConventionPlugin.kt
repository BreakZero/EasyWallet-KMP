import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.easy.configs.configureFlavors
import com.easy.configs.configurePrintApksTask
import com.easy.configs.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode

class MultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.native.cocoapods")
            }

            extensions.configure<LibraryExtension> {
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                compileSdk = AndroidBuildConfig.compileSdkVersion
                defaultConfig {
                    minSdk = AndroidBuildConfig.minSdkVersion
                }
                configureFlavors(this)
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }

            extensions.configure<KotlinMultiplatformExtension> {
//              jvmToolchain(17)
                androidTarget {
                    compilations.all {
                        kotlinOptions {
                            jvmTarget = JavaVersion.VERSION_17.toString()
                        }
                    }
                }
                iosX64()
                iosArm64()
                iosSimulatorArm64()

                val commonMain = sourceSets.getByName("commonMain") {
                    dependencies {
                        implementation(libs.findLibrary("koin.core").get())
                    }
                }
                val commonTest = sourceSets.getByName("commonTest")
                val iosX64Main = sourceSets.getByName("iosX64Main")
                val iosArm64Main = sourceSets.getByName("iosArm64Main")
                val iosSimulatorArm64Main = sourceSets.getByName("iosSimulatorArm64Main")
                sourceSets.create("iosMain") {
                    dependsOn(commonMain)
                    iosX64Main.dependsOn(this)
                    iosArm64Main.dependsOn(this)
                    iosSimulatorArm64Main.dependsOn(this)
                }
                val iosX64Test = sourceSets.getByName("iosX64Test")
                val iosArm64Test = sourceSets.getByName("iosArm64Test")
                val iosSimulatorArm64Test = sourceSets.getByName("iosSimulatorArm64Test")
                sourceSets.create("iosTest") {
                    dependsOn(commonTest)
                    iosX64Test.dependsOn(this)
                    iosArm64Test.dependsOn(this)
                    iosSimulatorArm64Test.dependsOn(this)
                }

                (this as ExtensionAware).extensions.configure<CocoapodsExtension> {
                    val isDataModule = project.name == "data"
                    summary = "Wallet cocoapod submodule"
                    homepage = "dejinlu.com"
                    authors = "Dougie"
                    version = "1.0"
                    ios.deploymentTarget = "16.0"
                    framework {
                        baseName = project.name
                        isStatic = isDataModule
                        if (!isStatic) {
                            embedBitcode(BitcodeEmbeddingMode.BITCODE)
                        }
                    }
                }
            }
        }
    }
}
