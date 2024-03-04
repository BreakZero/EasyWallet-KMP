//
//  HomeScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import SwiftUICharts

struct HomeScreen: View {
    @ObservedObject private var viewModel = HomeViewModel()
    var body: some View {
        VStack {
            switch viewModel.homeUiState {
            case .Fetching:
                ProgressView()
            case .GuestUiState(_):
                GuestView()
            case .WalletUiState(let dashboard):
                VStack {
                    List {
                        Section(
                            header: LineChart().data(dashboard.moneyTrend).frame(height: 150).chartStyle(ChartStyle(
                                backgroundColor: .white,
                                foregroundColor: ColorGradient(.blue, .purple))
                            ).listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 12, trailing: 0)),
                            content: {
                                ForEach(dashboard.tokens,id: \.self.token.id) { token in
                                    TokenItemView(extraToken: token)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
