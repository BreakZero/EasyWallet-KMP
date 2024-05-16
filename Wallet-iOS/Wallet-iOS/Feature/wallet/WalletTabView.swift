//
//  HomeScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import platform_shared
import SwiftfulRouting

struct WalletTabView: View {
    @Environment(\.router) var router
    
    @ObservedObject private var viewModel = WalletViewModel()
    
    @State private var showHeader: Bool = true
    
    
    var body: some View {
        ZStack {
            Color.white.ignoresSafeArea()
            
            ScrollView(.vertical) {
                LazyVStack(spacing: 12) {
                    CoinListHeaderCell()
                        .readingFrame { frame in
                            showHeader = frame.maxY < 150 ? true : false
                        }
                    
                    switch viewModel.walletUiState {
                    case .Fetching:
                        ProgressView()
                    case .GuestUiState(_):
                        GuestUserView()
                    case .UserUiState(let allAssetDashboardInformation):
                        ForEach(allAssetDashboardInformation.assetBalances, id: \.id) {assetBalance in
                            CoinRowCell(assetBalance: assetBalance)
                                .padding(.horizontal, 16)
                                .asButton(.press) {
                                    
                                }
                        }
                    }
                }
            }.onFirstAppear {
                viewModel.fetching()
            }.ignoresSafeArea()
                .scrollIndicators(.hidden)
            
            ZStack {
                Image(systemName: "gear")
                    .font(.title3)
                    .padding(10)
                    .background(showHeader ? Color.gray.opacity(0.001) : Color.gray.opacity(0.7))
                    .clipShape(Circle())
                    .padding(.leading, 16)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }.foregroundStyle(.white)
                .animation(.smooth(duration: 0.2), value: showHeader)
                .background(showHeader ? Color.accentColor : Color.clear)
                .frame(maxHeight: .infinity, alignment: .top)
        }
    }
}

#Preview {
    WalletTabView()
}
