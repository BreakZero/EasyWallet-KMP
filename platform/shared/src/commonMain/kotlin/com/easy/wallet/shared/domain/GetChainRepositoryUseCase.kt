package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.AssetPlatformIdConstant
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.shared.data.repository.chain.OnChainRepository

internal class GetChainRepositoryUseCase(
    private val evmChainRepository: OnChainRepository,
    private val noSupportedChainRepository: OnChainRepository
) {
    operator fun invoke(platform: AssetPlatform): OnChainRepository {
        return when (platform.id) {
            AssetPlatformIdConstant.PLATFORM_ETHEREUM, AssetPlatformIdConstant.PLATFORM_ETHEREUM_SEPOLIA -> evmChainRepository
            else -> noSupportedChainRepository
        }
    }
}
