## 关于EasyWallet
EasyWallet初衷是一个为CryptoDeFiWallet重构准备而实验的一个项目，同时也是为了学习Android新技术栈的练习项目。目前使用Kotlin Multiplatform完成Android
和iOS开发，共享逻辑代码，UI分别使用Jetpack Compose和SwiftUI实现。

|                                                                                                       |                                                                                                         |                                                                                                           |
|:-----------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|
| ![Home](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_100157.png) | ![Drawer](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_100109.png) | ![Settings](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_100215.png) |
| ![Chat](https://github.com/BreakZero/EasyWallet-KMP/blob/main/screens/Screenshot_20240111_100239.png) |                                                                                                         |                                                                                                           |

### 配置
由于项目中依赖[wallet-core](https://github.com/trustwallet/wallet-core)实现区块链相关功能处理，其使用`GitHub packages`进行包管理，所以想要拉去依赖库，需要使用到`github token`，
所以需要创建一个文件命名为`github_token.properties`，并填入以下内容：
```properties
gpr.name=Your Github Name
gpr.key=Your Github token
```
更多请看[library-dependency](https://developer.trustwallet.com/developer/wallet-core/integration-guide/android-guide#adding-library-dependency)

### 各链数据接口
不同链的数据来源也不同，所以需要不同apikey，可以查看[NetworkModule.kt](platform%2Fnetwork%2Fsrc%2FcommonMain%2Fkotlin%2Fcom%2Feasy%2Fwallet%2Fnetwork%2Fdi%2FNetworkModule.kt)中补上。
> 计划实现一个服务端，加上Redis实现缓存，统一处理数据来源结构
