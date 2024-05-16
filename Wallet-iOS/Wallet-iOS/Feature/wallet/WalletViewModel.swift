//
//  HomeViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Combine
import platform_shared
import KMPNativeCoroutinesAsync

extension WalletTabView {
    @MainActor final class WalletViewModel: ObservableObject {
        @LazyKoin private var dashboardUseCase: AllAssetDashboardUseCase
        @LazyKoin private var multiWalletRepository: MultiWalletRepository
        
        @Published private(set) var walletDashboard: AllAssetDashboardInformation? = nil
        @Published private(set) var isChecking: Bool = true
        
        func checkWallet() {
            Task {
                try? await asyncSequence(for: multiWalletRepository.findWalletStream()).collect { wallet in
                    isChecking = false
                    if wallet != nil {
                        await loading(wallet: wallet!)
                    }
                }
            }
        }
        
        private func loading(wallet: ModelWallet) async {
            try? await asyncSequence(for: dashboardUseCase.invoke(wallet: wallet)).collect {dashboard in
                self.walletDashboard = dashboard
            }
        }
    }
}
