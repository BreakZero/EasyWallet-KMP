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
        @LazyKoin private var dashboardUseCase: DashboardUseCase
        @LazyKoin private var multiWalletRepository: MultiWalletRepository
        
        private let moneyTrend: [Double] = [8,2,4,6,12,9,2]
        
        @Published private(set) var homeUiState: HomeUiState = HomeUiState.Fetching
        
        func fetching() async {
            try? await asyncSequence(for: multiWalletRepository.forActivatedOne()).collect { wallet in
                print("wallet \(wallet?.mnemonic ?? "empty...")")
                if wallet != nil {
                    homeUiState = HomeUiState.Fetching
                    await startLoadToken(wallet: wallet!)
                } else {
                    homeUiState = HomeUiState.GuestUiState("Guest User")
                }
            }
        }
        
        private func startLoadToken(wallet: ModelWallet) async {
            try? await asyncSequence(for: dashboardUseCase.invoke(wallet: wallet)).collect { tokens in
                print("token size: \(tokens.count.description)")
                self.homeUiState = HomeUiState.WalletUiState(HomeUiState.Dashboard(user: "User Name", moneyTrend: self.moneyTrend, tokens: tokens))
            }
        }
    }
}
