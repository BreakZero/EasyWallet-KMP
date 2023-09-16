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
        private var disposables = Set<AnyCancellable>()

        @Published private(set) var tokens:[ExtraToken] = []

        init() {
            createPublisher(wrapper: dashboard.dashboard())
                .sink(
                    receiveCompletion: { completion in switch completion {
                    case .finished:
                        print("Completed with success")
                        break
                    case let .failure(throwable):
                        print("Completed with failure: \(throwable)")
                        break
                    }
                    },
                    receiveValue: { tokens in
                        self.tokens = tokens as! [ExtraToken]
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
