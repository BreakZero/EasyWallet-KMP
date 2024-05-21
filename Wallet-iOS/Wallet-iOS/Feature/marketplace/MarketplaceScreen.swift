//
//  MarketplaceScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MarketplaceScreen: View {
    
    @ObservedObject private var viewModel: ViewModel = ViewModel()
    
    var body: some View {
        
        ZStack {
            LinearGradient(colors: [Color.clear, .accentColor], startPoint: .top, endPoint: .bottom).ignoresSafeArea()
            
            ScrollView(.vertical) {
                LazyVStack(spacing: 8) {
                    ForEach(viewModel.marketData, id: \.id) { info in
                        CoinMarketRowCell(
                            coinLogoUri: info.image,
                            coinName: info.name,
                            coinSymbol: info.symbol,
                            coinPrice: "\(info.currentPrice) USD",
                            percent: "\(info.priceChangePercentage24h) %"
                        )
                    }
                }
            }
        }.task {
            await viewModel.initPaging()
        }.task {
            await viewModel.subscribeDataChanged()
        }.task {
            await viewModel.subscribeLoadStateChanged()
        }
    }
}

#Preview {
    MarketplaceScreen()
}
