//
//  HomeScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import platform_shared

struct HomeScreen: View {
    @ObservedObject private var viewModel = HomeViewModel()
    var body: some View {
        VStack {
            switch viewModel.walletUiState {
            case .Fetching:
                ProgressView()
            case .GuestUiState(_):
                GuestUserView()
            case .UserUiState(let dashboard):
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
    dashboard: AllAssetDashboardInformation
) -> some View {
    NavigationStack {
        List {
            Section(
                header: Text("\(dashboard.fiatBalance) \(dashboard.fiatSymbol)"),
                content: {
                    ForEach(dashboard.assetBalances, id: \.id) { assetBalance in
                        ZStack {
                            CoinItemView(assetBalance: assetBalance)
                            NavigationLink(destination: {
                                TransactionScreen(coinId: assetBalance.id)
                            }, label: {
                                EmptyView()
                            }).opacity(0.0)
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
    TokenListView(dashboard: AllAssetDashboardInformation(fiatSymbol: "USD", fiatBalance: "8.88", assetBalances: []))
}
