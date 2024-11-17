## 关于
`Composing Build` + Version catalogs 作为Android推荐的包管理方式。

`Build Logic`通过参考`nowinandroid`加上个人开发经验提取整合出来的，可作为submodule使用，可以大量减少配置文件，方便管理。

通过插件的方式提供了多种相关依赖整合管理，亦可以通过组合插件来实现多样话配置，从而简化Android/Kotlin Multiplatform项目的Gradle配置模板代码。 对*modularization*项目更友好。 

[介绍](https://www.dejinlu.com/posts/2023/09/27/ddd9439e/)

## 使用
1. 作为submodule应用到项目中 
    在已进行git初始化的项目下使用如下命令添加submodule
    ```shell
    git submodule add https://github.com/BreakZero/build-logic
    ```

2. 根据需求创建catalog文件(`libs.versions.toml`)进行依赖包管理
   - 在`gradle`目录下新建文件`libs.versions.toml`(当然可以按照喜好命名，反正可以配置)
   - 在`settings.gradle.kts`文件中增加插件管理配置，如下：
   ```kotlin
   dependencyResolutionManagement {
      repositories {
          ***
      }
      versionCatalogs {
          create("easy") {
              from(files("./build-logic/building.versions.toml"))
          }
      }
   }
   ```
   > Note: 也可以不需要这个配置，直接使用正确的plugin id便可正常使用。id 可从这里[building.versions.toml](building.versions.toml)拷贝
   > 
   > 另外本项目提供一些常用的依赖包，请参考[catalogs](catalogs), 版本号之类的可能已经比较老，自行更新

3. 同步项目并根据情况更改任何module中的`build.gradle.kts`文件
   - 插件使用方式
   ```kotlin
   plugins {
       alias(libs.plugins.android.application) apply false
       alias(libs.plugins.android.library) apply false
       alias(libs.plugins.kotlin.android) apply false
   }
   ```
   ```kotlin
   plugins {
       id("org.easy.android.application.compose")
       id("org.easy.jacoco")
       id("org.easy.hilt")
   }
   ```
    
    - 依赖库使用方式
    ```kotlin
    dependencies {
        implementation(libs.core.ktx)
        implementation(libs.androidx.compose.activity)
    }
    ```    

## 例子参考
一个简单的使用例子 [Example](https://github.com/BreakZero/Build-Logic-UsingExample)

## 其他
在Android Platform配置上，对应的`compileSdkVersion`、`targetSdkVersion`、`versionCode`等相关依然在本项目中，无法让主项目来管理。

但是可以通过在**module**中进行覆盖配置。不建议如此，只需要在app module上进行对应的覆盖就好，对于`Library` module基本也不会考虑。

## 参考
[nowinandroid](https://github.com/android/nowinandroid)

## README.md
- English [README.md](README.md)