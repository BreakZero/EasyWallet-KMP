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
        private let tokenWrapper = TokenWrapper()
        private var disposables = Set<AnyCancellable>()
        
        @Published private(set) var tokens:[ModelToken] = []
        
        init() {
            createPublisher(wrapper: tokenWrapper.tokensStream())
                .sink(receiveCompletion: { completion in switch completion {
                case .finished:
                    print("Completed with success")
                    break
                case let .failure(throwable):
                    print("Completed with failure: \(throwable)")
                    break
                }
                    
                }, receiveValue: { tokens in
                    self.tokens = tokens as! [ModelToken]
                }).store(in: &disposables)
        }
        
        func onCleared() {
            disposables.forEach { disposable in
                disposable.cancel()
            }
        }
    }
}
