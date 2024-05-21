//
//  MarketplaceViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/21.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import platform_shared
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesCore
import KMPNativeCoroutinesAsync

extension MarketplaceScreen {
    @MainActor
    final class ViewModel: ObservableObject {
        private var delegate = PagingCollectionViewController<ModelCoinMarketInformation>()
        
        @LazyKoin private var marketRepository: MarketsRepository
        
        @Published private(set) var transactions:[ModelCoinMarketInformation] = []
        @Published private(set) var hasNextPage: Bool = false
        @Published private(set) var showLoading: Bool = false
        
        func fetching() async {
            
        }
    }
}
