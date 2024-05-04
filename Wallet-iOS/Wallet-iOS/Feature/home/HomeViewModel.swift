//
//  HomeViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Combine
import shared
import KMPNativeCoroutinesAsync

extension HomeScreen {
    @MainActor final class HomeViewModel: ObservableObject {
        @LazyKoin private var dashboardUseCase: AllAssetDashboardUseCase
        @LazyKoin private var multiWalletRepository: MultiWalletRepository
        
        private let moneyTrend: [Double] = [8,2,4,6,12,9,2]
        
        @Published private(set) var homeUiState: HomeUiState = HomeUiState.Fetching
        @Published private(set) var walletExist: Bool = false
        
        func fetching() {
            Task {
                try? await asyncSequence(for: multiWalletRepository.findWalletStream()).collect { wallet in
                    print("wallet \(wallet?.mnemonic ?? "empty...")")
                    if wallet != nil {
                        homeUiState = HomeUiState.Fetching
                        await startLoadToken(wallet: wallet!)
                    } else {
                        homeUiState = HomeUiState.GuestUiState("Guest User")
                    }
                }
            }
        }
        
        private func startLoadToken(wallet: ModelWallet) async {
            try? await asyncSequence(for: dashboardUseCase.invoke(wallet: wallet)).collect { dashboard in
                print("token size: \(dashboard.fiatBalance)")
                self.homeUiState = HomeUiState.WalletUiState(dashboard)
            }
        }
    }
}
