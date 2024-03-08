package com.easy.wallet.token_manager.chain.editor

import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.ChainRepository

internal class ChainEditorViewModel(
    private val localChainRepository: ChainRepository
) : BaseViewModel<ChainEditorUiEvent>() {
    override fun handleEvent(event: ChainEditorUiEvent) {
        TODO("Not yet implemented")
    }
}
