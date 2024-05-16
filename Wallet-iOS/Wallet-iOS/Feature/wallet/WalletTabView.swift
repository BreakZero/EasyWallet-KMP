//
//  HomeScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import platform_shared

struct WalletTabView: View {
    @ObservedObject private var viewModel = WalletViewModel()
    
    @State private var showHeader: Bool = true
    
    
    var body: some View {
        if viewModel.isChecking {
            VStack {
                ProgressView().onFirstAppear {
                    viewModel.checkWallet()
                }
            }
        } else {
            NavigationStack {
                ZStack {
                    LinearGradient(colors: [Color.clear, .accentColor], startPoint: .top, endPoint: .bottom).edgesIgnoringSafeArea(.top)
                    
                    ScrollView(.vertical) {
                        LazyVStack(spacing: 12) {
                            if viewModel.walletDashboard == nil {
                                GuestUserView()
                            } else {
                                CoinListHeaderCell()
                                    .readingFrame { frame in
                                        showHeader = frame.maxY < 150 ? true : false
                                    }
                                
                                ForEach(viewModel.walletDashboard!.assetBalances, id: \.id) {assetBalance in
                                    NavigationLink(value: assetBalance.id, label: {
                                        CoinRowCell(assetBalance: assetBalance)
                                            .padding(.horizontal, 16)
                                    }).buttonStyle(PlainButtonStyle())
                                }
                            }
                        }
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
                }.navigationDestination(for: String.self) {value in
                    TransactionScreen(coinId: value)
                }
            }
        }
    }
}

#Preview {
    WalletTabView()
}
