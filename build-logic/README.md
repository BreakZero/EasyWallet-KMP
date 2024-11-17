## About
The way of Android Project package management, the commendation is `Composing Build` + Version catalogs.

`Build Logic` is the production that Referring `nowinandroid` with my experience in Android Project. 

You can use it as submodule in your project that can reduce lot of your gradle configuration code.

Provides many plugins that category of feature/functional..., also can combine multi plugins for your module's variety.

## Usage
1. Using as submodule in your project
    in a git initialed project, run command line as below:
    ```shell
    git submodule add https://github.com/BreakZero/build-logic
    ```
2. Creating version catalog file
   - create a file named `libs.versions.toml` under **gradle** folder
   - add `versionCatalogs` block configuration into **settings.gradle.kts** file within `dependencyResolutionManagement`
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
   > Note: You also can use plugin id directly without doing this step. Plugin id see [building.versions.toml](building.versions.toml)
   > 
   > There are some common libraries provided in [catalogs](catalogs), version maybe older, update if you need

3. Sync project and using that you need
   - Plugin usage
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
   - Dependencies usage
   ```kotlin
   dependencies {
        implementation(libs.core.ktx)
        implementation(libs.androidx.compose.activity)
   }
   ```

## Example
Here is an [Example](https://github.com/BreakZero/Build-Logic-UsingExample)

## Others
There are some config fields could not move to **Main Project** such as `compileSdkVersion`、`targetSdkVersion`、`versionCode`...

But you also can override it in **module** configuration file. 

Actually, i don't recommend do in that way. In Android Project, we don't care about the `Library module` version code, so we 
do not need manage it in specially. Just override in app module.

## Referring
[nowinandroid](https://github.com/android/nowinandroid)

## README.md
- Chinese [README_CN.md](README_CN.md)
