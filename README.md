### ABOUT

### Setup
#### For Android
> Cause gradle add keystore to signing app that using into DevOps, there are two options pass build progress.
- make a directory named `keystore` and create 2 files named `keystore.jks`, `keystore.properties`, properties content like below(update the value that you set)
```properties
storePassword=***
keyPassword=***
keyAlias=***
storeFile=keystore/keystore.jks
```
- delete the signingConfigs block in the Android app build.gradle.kts