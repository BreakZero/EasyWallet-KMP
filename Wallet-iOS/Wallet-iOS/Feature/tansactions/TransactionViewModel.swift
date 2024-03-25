//
//  TransactionViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/25.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

extension TransactionScreen {
    @MainActor final class ViewModel: ObservableObject {
        
        @LazyKoin private var tokenAmountUseCase: TokenAmountUseCase
        @LazyKoin private var coinTrendUseCase: CoinTrendUseCase
        @LazyKoin private var tnxPagerUseCase: TransactionPagerUseCase
        
        func loading(tokenId: String) async {
            await tokenAmountUseCase.invoke(tokenId: tokenId).collect { balance in
                print("====== \(balance)")
            }
        }
    }
}
