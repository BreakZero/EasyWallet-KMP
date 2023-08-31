//
//  HomeViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension HomeScreen {
    @MainActor final class HomeViewModel: ObservableObject {
        @Published private(set) var existAccount: Bool = false
    }
}
