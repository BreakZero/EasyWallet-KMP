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
import AsyncExtensions

//extension HomeScreen {
//    @MainActor final class HomeViewModel: ObservableObject {
//        private let dashboard = HomeDashboardComponent()
//        private let globalComponent = GlobalEnvComponent()
//        private let multiWalletRepository = MultiWalletComponent()
//        private var disposables = Set<AnyCancellable>()
//        
//        private let moneyTrend: [Double] = [8,2,4,6,12,9,2]
//
//        @Published private(set) var homeUiState: HomeUiState = HomeUiState.Fetching
//
//        init() {
//            
//            createPublisher(wrapper: multiWalletRepository.forActivatedOne())
//                .map { wallet in
//                    if wallet != nil {
//                        self.globalComponent.loadInMemory(mnemonic: wallet!.mnemonic, passphrase: "")
//                        self.startTokenFetching()
//                        return HomeUiState.Fetching
//                    } else {
//                        return HomeUiState.GuestUiState("Guest User")
//                    }
//                }.subscribe(on: DispatchQueue.main)
//                .sink(
//                    receiveCompletion: { completion in
//                        switch completion {
//                        case .finished:
//                            print("Completed with success")
//                            break
//                        case let .failure(throwable):
//                            print("Completed with failure: \(throwable)")
//                            break
//                        }
//                    },
//                    receiveValue: { uiState in
//                        print("=====--- \(uiState)")
//                        self.homeUiState = uiState
//                    }
//                ).store(in: &disposables)
//        }
//
//        private func startTokenFetching() {
////            createPublisher(wrapper: dashboard.dashboard())
////                .subscribe(on: DispatchQueue.main)
////                .sink(
////                    receiveCompletion: { _ in },
////                    receiveValue: { tokens in
////                        self.homeUiState = HomeUiState.WalletUiState(HomeUiState.Dashboard(user: "Dougie", moneyTrend: self.moneyTrend, tokens: tokens as! [TokenUiModel]))
////                    }
////                ).store(in: &disposables)
//        }
//
//        func onCleared() {
//            disposables.forEach { disposable in
//                disposable.cancel()
//            }
//        }
//    }
//}

extension HomeScreen {
    @MainActor final class HomeViewModel: ObservableObject {
        @LazyKoin private var dashboardUseCase: DashboardUseCase
        @LazyKoin private var multiWalletRepository: MultiWalletRepository
        
        private let moneyTrend: [Double] = [8,2,4,6,12,9,2]
        
        @Published private(set) var homeUiState: HomeUiState = HomeUiState.Fetching
        
        func fetching() async {
            await multiWalletRepository.forActivatedOne().collect { wallet in
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
            await dashboardUseCase.invoke(wallet: wallet).collect { tokens in
                self.homeUiState = HomeUiState.WalletUiState(HomeUiState.Dashboard(user: "Dougie", moneyTrend: self.moneyTrend, tokens: tokens))
            }
        }
    }
}
