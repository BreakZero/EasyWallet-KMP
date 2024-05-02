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
    
    private var tokenId: String
    
    init(tokenId: String) {
        self.tokenId = tokenId
    }
    
    var body: some View {
        VStack {
            if (viewModel.showLoading) {
                ProgressView()
            } else {
                List {
                    Dashboard(dashboard: viewModel.transactionDashboard)
                        .frame(height: 200)
                        .listRowInsets(EdgeInsets())
                        .listRowBackground(Color.clear)
                    
                    ForEach(viewModel.transactions, id: \.hash_) { transaction in
                        TransactionSummaryView(transaction: transaction)
                            .listRowInsets(EdgeInsets())
                            .listRowBackground(Color.clear)
                    }
                    
                    if (!viewModel.transactions.isEmpty) {
                        VStack(alignment: .center) {
                            if(viewModel.hasNextPage) {
                                ProgressView().onAppear {
                                    viewModel.loadNextPage()
                                }
                            } else {
                                Text("-- Not more --").foregroundColor(.gray)
                            }
                        }.frame(maxWidth: .infinity).listRowInsets(EdgeInsets())
                            .listRowBackground(Color.clear)
                    }
                }
            }
        }.task {
            await viewModel.loading(tokenId: tokenId)
        }.task {
            await viewModel.initPaging(tokenId: tokenId)
        }.task {
            await viewModel.subscribeDataChanged()
        }.task {
            await viewModel.subscribeLoadStateChanged()
        }
    }
}

@ViewBuilder
private func Dashboard(dashboard: TransactionDashboard) -> some View {
    ZStack(alignment: .top) {
        Chart {
            ForEach(dashboard.marketPrices, id: \.id) { item in
                LineMark(x: .value("index", item.index), y: .value("value", item.price))
            }
        }
    }.padding().frame(maxWidth: .infinity).background(Color.blue.opacity(0.5))
        .clipShape(RoundedRectangle(cornerSize: CGSize(width: 20, height: 10)))
}
