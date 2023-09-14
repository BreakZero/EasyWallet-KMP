//
//  HomeViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import data

extension HomeScreen {
    @MainActor final class HomeViewModel: ObservableObject {
        @Published private(set) var existAccount: Bool = false

        init() {
            let task = Task {
                let result = try await suspend(TokenWrapper().loadTokens())
                print("pre count: \(result.count)")
//                 let tokens: [DatabaseToken] = result as! [DatabaseToken]
//                 print(tokens.count)
//                 tokens.forEach {token in
//                     print("=====")
//                 }
            }
//            task.cancel()
        }
    }
}
