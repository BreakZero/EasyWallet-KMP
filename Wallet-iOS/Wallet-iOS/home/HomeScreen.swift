//
//  HomeScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeScreen: View {
    @ObservedObject private var viewModel = HomeViewModel()
    var body: some View {
        VStack {
            switch viewModel.homeUiState {
            case .Fetching:
                ProgressView()
            case .GuestUiState(_):
                Text("Dougie")
            case .WalletUiState(let dashboard):
                VStack {
                    List {
                        Section(
                            content: {
                                ForEach(dashboard.tokens,id: \.self.token.id) { token in
                                    TokenItemView(extraToken: token)
                                }
                            }
                        )
                    }
                }
            }
        }.task {
            await viewModel.fetching()
        }
    }
}
