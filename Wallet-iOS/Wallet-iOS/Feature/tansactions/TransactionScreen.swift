//
//  TransactionScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/25.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Charts

struct TransactionScreen: View {
    @ObservedObject private var viewModel = ViewModel()
    
    private var coinId: String
    
    init(coinId: String) {
        self.coinId = coinId
    }
    
    var body: some View {
        ZStack {
            LinearGradient(colors: [Color.clear, .accentColor], startPoint: .top, endPoint: .bottom).ignoresSafeArea()
            
            ScrollView(.vertical) {
                LazyVStack(spacing: 8) {
                    TransactionDashboardHeader(
                        dashboard: viewModel.transactionDashboard
                    )
                    ForEach(viewModel.transactions, id: \.hash_) { transaction in
                        TransactionRowCell(transaction: transaction)
                    }
                }
            }
        }.task {
            await viewModel.loading(coinId: coinId)
        }.task {
            await viewModel.initPaging(coinId: coinId)
        }.task {
            await viewModel.subscribeDataChanged()
        }.task {
            await viewModel.subscribeLoadStateChanged()
        }.toolbar(.hidden, for: .tabBar)
    }
}

#Preview {
    TransactionScreen(coinId: "ethereum")
}
