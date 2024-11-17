package org.easy.mobile.convention

import org.gradle.api.Project
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

object AndroidBuildConfig {
    const val compileSdkVersion = 35
    const val minSdkVersion = 28
    const val targetSdkVersion = 35
}

/**
 * @param path 文件路径，相对于rootProject的路径
 */
fun Project.getPropertiesByFile(path: String): Properties {
    val properties = Properties()
    val keyPropertiesFile = rootProject.file(path)

    if (keyPropertiesFile.isFile) {
        InputStreamReader(FileInputStream(keyPropertiesFile), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    }
    return properties
}