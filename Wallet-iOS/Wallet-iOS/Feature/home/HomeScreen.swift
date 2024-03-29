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
                GuestUserView()
            case .WalletUiState(let dashboard):
                TokenListView(dashboard: dashboard)
            }
        }.task {
            await viewModel.fetching()
        }
    }
}

@ViewBuilder
private func TokenListView(
    dashboard: HomeUiState.Dashboard
) -> some View {
    NavigationStack {
        List {
            Section(
                header: Text(dashboard.user),
                content: {
                    ForEach(dashboard.tokens,id: \.self.token.id) { token in
                        ZStack {
                            TokenItemView(extraToken: token)
                            NavigationLink(destination: {
                                TransactionScreen(tokenId: token.token.id)
                            }) {
                                EmptyView()
                            }.opacity(0.0)
                        }.listRowBackground(Color.clear)
                    }
                }
            )
        }.toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Image(systemName: "gear")
            }
        }
    }
}

#Preview {
    TokenListView(dashboard: HomeUiState.Dashboard(user: "Dougie", moneyTrend: [], tokens: []))
}
