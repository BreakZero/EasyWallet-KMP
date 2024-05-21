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
        
        @Published private(set) var marketData:[ModelCoinMarketInformation] = []
        @Published private(set) var hasNextPage: Bool = false
        @Published private(set) var showLoading: Bool = false
        
        func fetching() async {
            try? await asyncSequence(for: marketRepository.searchTrends()).collect { markets in
                print("===== \(markets.count)")
            }
        }
        
        func initPaging() async {
            let marketStream = marketRepository.topCoinsByPagingFlow(currency: "USD")
            try? await asyncSequence(for: marketStream).collect { pagingData in
                try? await delegate.submitData(pagingData: pagingData)
            }
        }
        
        func loadNextPage() {
            delegate.loadNextPage()
        }
        
        func subscribeDataChanged() async {
            do {
                for try await _ in asyncSequence(for: delegate.onPagesUpdatedFlow) {
                    self.marketData = delegate.getItems()
                }
            } catch {
                
            }
        }
        
        func subscribeLoadStateChanged() async {
            do {
                for try await loadState in asyncSequence(for: delegate.loadStateFlow) {
                    switch onEnum(of: loadState.append) {
                    case .error(let errorState):
                        print(errorState.error.message?.description ?? "append error...")
                        break
                    case .loading(_):
                        break
                    case .notLoading(let notLoading):
                        self.hasNextPage = !notLoading.endOfPaginationReached
                        break
                    }
                    
                    switch onEnum(of: loadState.refresh) {
                    case .error(_):
                        break
                    case .loading(_):
                        self.showLoading = true
                        break
                    case .notLoading(_):
                        self.showLoading = false
                        break
                    }
                }
            } catch {
                
            }
        }
    }
}
