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
                TokenListView(dashboard: dashboard).refreshable {
                    viewModel.fetching()
                }
            }
        }.onFirstAppear {
            viewModel.fetching()
        }
    }
}

@ViewBuilder
private func TokenListView(
    dashboard: DashboardInformation
) -> some View {
    NavigationStack {
        List {
            Section(
                header: Text("\(dashboard.fiatBalance) \(dashboard.fiatSymbol)"),
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
    TokenListView(dashboard: DashboardInformation(fiatSymbol: "USD", fiatBalance: "8.88", tokens: []))
}
