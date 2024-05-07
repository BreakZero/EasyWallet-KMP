package com.easy.wallet.shared.domain

import co.touchlab.kermit.Logger
import com.easy.wallet.core.commom.AssetPlatformIdConstant
import com.easy.wallet.model.Wallet
import com.easy.wallet.model.asset.AssetBalance
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.shared.data.repository.asset.CoinRepository
import com.easy.wallet.shared.model.AllAssetDashboardInformation
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class AllAssetDashboardUseCase internal constructor(
    private val coinRepository: CoinRepository,
    private val getChainRepositoryUseCase: GetChainRepositoryUseCase
) {
    @NativeCoroutines
    operator fun invoke(wallet: Wallet): Flow<AllAssetDashboardInformation> {
        val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
        return channelFlow {
            val coins = coinRepository.findAllCoin()
            val emptyBalances = coins.map { coin ->
                val address = hdWallet.getAddressByPlatform(coin.platform)
                AssetBalance.copyFromBasicCoin(coin, address, "0.00")
            }

            send(
                AllAssetDashboardInformation(
                    fiatBalance = "888.88",
                    fiatSymbol = "USD",
                    assetBalances = emptyBalances
                )
            )
            val assetBalances = emptyBalances.map { coin ->
                val chainRepository = getChainRepositoryUseCase(coin.platform)
                coroutineScope {
                    async {
                        val balance = try {
                            chainRepository.loadBalance(
                                platform = coin.platform,
                                coin.address,
                                coin.contract
                            ).toBigDecimal()
                        } catch (e: Exception) {
                            Logger.w("AllAssetDashboardUseCase: ") {
                                e.message ?: "unknown error"
                            }
                            BigDecimal.ZERO
                        }
                        val displayBalance = balance.moveDecimalPoint(-coin.decimalPlace)
                            .roundToDigitPositionAfterDecimalPoint(
                                digitPosition = 8,
                                roundingMode = RoundingMode.ROUND_HALF_CEILING
                            )
                            .toPlainString()
                        coin.copy(balance = displayBalance)
                    }
                }
            }.awaitAll()
            send(
                AllAssetDashboardInformation(
                    fiatBalance = "888.88",
                    fiatSymbol = "USD",
                    assetBalances = assetBalances
                )
            )
        }
    }
}

/** group version (maybe group by group id or name
 val balanceMap = mutableMapOf<String, BigDecimal>()
// zip balance by coin id
emptyBalances.map { coin ->
    coroutineScope {
        val chainRepository = getChainRepositoryUseCase(coin.platform)
        async {
            val balance = try {
                chainRepository.loadBalance(
                    platform = coin.platform,
                    coin.address,
                    coin.contract
                ).toBigDecimal()
            } catch (e: Exception) {
                Logger.w("AllAssetDashboardUseCase: ") {
                    e.message ?: "unknown error"
                }
                BigDecimal.ZERO
            }
            if (balanceMap.containsKey(coin.name)) {
                balanceMap[coin.name] = balanceMap[coin.id]!!.plus(balance)
            } else {
                balanceMap[coin.name] = balance
            }
        }
    }
}.awaitAll()
val distinctCoins = emptyBalances.distinctBy { it.name }
val assetBalances = distinctCoins.map {
    val balance = (balanceMap[it.name] ?: BigDecimal.ZERO)
        .moveDecimalPoint(-it.decimalPlace)
        .roundToDigitPositionAfterDecimalPoint(8, RoundingMode.ROUND_HALF_CEILING)
    it.copy(balance = balance.toPlainString())
}
 */

private fun HDWallet.getAddressByPlatform(platform: AssetPlatform): String {
    val coinType = when (platform.id) {
        AssetPlatformIdConstant.PLATFORM_ETHEREUM, AssetPlatformIdConstant.PLATFORM_ETHEREUM_SEPOLIA -> CoinType.Ethereum
        else -> null
    }
    return coinType?.let { this.getAddressForCoin(it) } ?: ""
}
