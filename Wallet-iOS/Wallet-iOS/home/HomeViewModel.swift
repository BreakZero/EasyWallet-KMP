//
//  HomeViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Combine
import data

extension HomeScreen {
    @MainActor final class HomeViewModel: ObservableObject {
        private let dashboard = HomeDashboardComponent()
        private let multiWalletRepository = MultiWalletComponent()
        private var disposables = Set<AnyCancellable>()
        
        @Published private(set) var homeUiState: HomeUiState = HomeUiState.Fetching
        
        init() {
            createPublisher(wrapper: multiWalletRepository.forActivatedOne())
                .map<HomeUiState> { wallet in
                    print(Thread.current)
                    if wallet != nil {
                        return HomeUiState.WalletUiState(HomeUiState.Dashboard(user: "",tokens: []))
                    } else {
                        print("=== wallet is empty")
                        return HomeUiState.GuestUiState("Guest User")
                    }
                }.sink(
                    receiveCompletion: { completion in
                        switch completion {
                        case .finished:
                            print("Completed with success")
                            break
                        case let .failure(throwable):
                            print("Completed with failure: \(throwable)")
                            break
                        }
                    },
                    receiveValue: { uiState in
                        print("==== \(Thread.current)")
                        self.homeUiState = uiState
                    }
                ).store(in: &disposables)
        }
        
        func onCleared() {
            disposables.forEach { disposable in
                disposable.cancel()
            }
        }
    }
}
