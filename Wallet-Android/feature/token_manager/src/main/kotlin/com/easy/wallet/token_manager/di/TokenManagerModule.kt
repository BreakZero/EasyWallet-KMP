package com.easy.wallet.token_manager.di

import com.easy.wallet.shared.data.repository.asset.LocalChainManageRepository
import com.easy.wallet.shared.data.repository.asset.LocalTokenManageRepository
import com.easy.wallet.token_manager.chain.detail.ChainDetailViewModel
import com.easy.wallet.token_manager.chain.editor.ChainEditorViewModel
import com.easy.wallet.token_manager.chain.manager.ChainManagerViewModel
import com.easy.wallet.token_manager.token.editor.TokenEditorViewModel
import com.easy.wallet.token_manager.token.manager.TokenManagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun assetModule() = module {
    viewModel {
        ChainManagerViewModel(get(named<LocalChainManageRepository>()))
    }
    viewModel {
        ChainEditorViewModel(get(named<LocalChainManageRepository>()), get())
    }

    viewModel {
        TokenManagerViewModel(get(named<LocalTokenManageRepository>()))
    }

    viewModel { ChainDetailViewModel(get(named<LocalChainManageRepository>()), get()) }

    viewModel {
        TokenEditorViewModel(
            get(named<LocalChainManageRepository>()),
            get(named<LocalTokenManageRepository>()),
            get()
        )
    }
}
