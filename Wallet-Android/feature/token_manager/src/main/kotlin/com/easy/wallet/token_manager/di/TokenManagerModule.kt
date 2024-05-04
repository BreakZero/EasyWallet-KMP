package com.easy.wallet.token_manager.di

import com.easy.wallet.token_manager.chain.detail.ChainDetailViewModel
import com.easy.wallet.token_manager.chain.editor.ChainEditorViewModel
import com.easy.wallet.token_manager.chain.manager.ChainManagerViewModel
import com.easy.wallet.token_manager.token.editor.TokenEditorViewModel
import com.easy.wallet.token_manager.token.manager.TokenManagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun assetModule() = module {
    viewModel {
        ChainManagerViewModel(get())
    }
    viewModel {
        ChainEditorViewModel(get(), get())
    }

    viewModel { ChainDetailViewModel(get(), get()) }

    viewModelOf(::TokenManagerViewModel)

    viewModelOf(::TokenEditorViewModel)
}
