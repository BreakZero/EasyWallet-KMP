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
        Text("Transaction List(WIP on iOS Platform)").task {
            await viewModel.loading(tokenId: tokenId)
        }
    }
}

#Preview {
    TransactionScreen(tokenId: "")
}
