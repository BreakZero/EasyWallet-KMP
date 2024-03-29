[![Android Release Build](https://github.com/BreakZero/EasyWallet-KMP/actions/workflows/android-build-release.yml/badge.svg)](https://github.com/BreakZero/EasyWallet-KMP/actions/workflows/android-build-release.yml)

## 关于EasyWallet - (WIP)
EasyWallet初衷是一个为CryptoDeFiWallet重构准备而实验的一个项目，同时也是为了学习Android新技术栈的练习项目。目前使用Kotlin Multiplatform进行Android
和iOS开发，共享逻辑代码。UI分别使用Jetpack Compose和SwiftUI实现。

### 配置
由于项目中依赖[wallet-core](https://github.com/trustwallet/wallet-core)实现区块链相关功能处理，其使用`GitHub packages`进行包管理，所以想拉取依赖库，需要使用到`github token`。
为了保证所有依赖都能成功下载，需要创建一个文件命名为`github_token.properties`，并填入以下内容：
```properties
gpr.name=Your Github Name
gpr.key=Your Github token
```
更多请看[library-dependency](https://developer.trustwallet.com/developer/wallet-core/integration-guide/android-guide#adding-library-dependency)

### 各链数据接口
- 旧版本：
不同链的数据来源也不同，所以需要不同apikey，可以查看[NetworkModule.kt](platform%2Fnetwork%2Fsrc%2FcommonMain%2Fkotlin%2Fcom%2Feasy%2Fwallet%2Fnetwork%2Fdi%2FNetworkModule.kt)中补上。

- 新版本：
使用本地配置来存储apikey，需要在`keystore`文件夹下新建一个名为`apikey.properties`的文件
目前使用到3个平台的apikey
```properties
etherscan=XWNRRIQY******K7YP4F6
coingecko=CG-8******AwuyrcgDPL
opensea=008e**********91dd6564880a
```
准备好之后，可执行任务生成文件
```shell
./gradlew -p platform generateBuildKonfig
```

### 开源依赖库
- [Skie](https://github.com/touchlab/SKIE)
- [Wallet-Core](https://github.com/trustwallet/wallet-core)
- [Coroutine](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) 用于异步
- [koin](https://github.com/InsertKoinIO/koin) 依赖注入
- [ktor](https://github.com/ktorio/ktor)
- [sqldelight](https://github.com/cashapp/sqldelight)
- [coil](https://github.com/coil-kt/coil)
- [Paging3](https://github.com/cashapp/multiplatform-paging)
- [BuildKonfig](https://github.com/yshrsmz/BuildKonfig)

### UI展示

|                                                                                                           |                                                                                                         |                                                                                                           |
|:---------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|
|   ![Home](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_101720.png)   | ![Drawer](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_101734.png) | ![Settings](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_101801.png) |
| ![Settings](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_101811.png) |  ![Home](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_100157.png)  |  ![Drawer](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_100109.png)  |
| ![Settings](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_100215.png) |  ![Chat](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_100239.png)  |                                                                                                           |                                                                                                           |

### 界面参考资源
- [Crypto-Wallet-Mobile-Ui-(Free)-(Community)](https://www.figma.com/file/sLqrdLp6vOedEnZgW1E3ze/Cryptooly---Crypto-Wallet-Mobile-Ui-(Free)-(Community)?type=design&mode=design)
- [CoinTrend](https://github.com/CoinTrend/CoinTrend)
