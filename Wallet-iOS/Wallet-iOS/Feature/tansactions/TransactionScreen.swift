//
//  TransactionScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/25.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TransactionScreen: View {
    @ObservedObject private var viewModel = ViewModel()
    
    private var tokenId: String
    
    init(tokenId: String) {
        self.tokenId = tokenId
    }
    
    var body: some View {
        VStack {
            if (viewModel.showLoding) {
                ProgressView()
            } else {
                List {
                    ForEach(viewModel.transactions, id: \.hash_) { transaction in
                        TransactionSummaryView(transaction: transaction)
                    }
                    
                    if (!viewModel.transactions.isEmpty) {
                        VStack {
                            if(viewModel.hasNextPage) {
                                ProgressView().onAppear {
                                    viewModel.loadNextPage()
                                }
                            } else {
                                VStack {
                                    Text("-- Not more --")
                                }
                            }
                        }
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

#Preview {
    TransactionScreen(tokenId: "")
}
