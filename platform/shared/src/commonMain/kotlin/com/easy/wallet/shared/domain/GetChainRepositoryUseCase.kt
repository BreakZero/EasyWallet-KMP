package com.easy.wallet.shared.domain

import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.shared.data.repository.platform.OnChainRepository

internal class GetChainRepositoryUseCase(
  private val evmChainRepository: OnChainRepository,
  private val noSupportedChainRepository: OnChainRepository
) {
  operator fun invoke(platform: AssetPlatform): OnChainRepository = when {
    platform.network != null -> evmChainRepository
    else -> noSupportedChainRepository
  }
}
