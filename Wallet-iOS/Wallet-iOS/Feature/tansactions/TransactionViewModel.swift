//
//  TransactionViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/25.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import platform_shared
import KMPNativeCoroutinesAsync
import Combine
import KMPNativeCoroutinesCombine

extension TransactionScreen {
    @MainActor final class ViewModel: ObservableObject {
        
        private var delegate = PagingCollectionViewController<TransactionUiModel>()
        
        @LazyKoin private var coinBalanceUseCase: CoinBalanceUseCase
        @LazyKoin private var coinTrendUseCase: CoinTrendUseCase
        @LazyKoin private var tnxPagerUseCase: TransactionPagerUseCase
        
        @Published private(set) var transactions:[TransactionUiModel] = []
        @Published private(set) var hasNextPage: Bool = false
        @Published private(set) var showLoading: Bool = false
        
        @Published private(set) var transactionDashboard: TransactionDashboard = TransactionDashboard(balance: "0.0", symbol: "", marketPrices: [])
        
        private var cancellable: AnyCancellable?
        
        func loading(coinId: String) {            
            let balancePublisher = createPublisher(for: coinBalanceUseCase.invoke(coinId: coinId))
            let trendPublisher = createPublisher(for: coinTrendUseCase.invoke(coinId: coinId))
            self.cancellable = Publishers.Zip(balancePublisher, trendPublisher)
                .sink {
                    print($0)
                } receiveValue: { (balance, trends) in
                    self.transactionDashboard = TransactionDashboard(
                        balance: balance.balance,
                        symbol: balance.symbol,
                        marketPrices: trends.enumerated().map({ (index, price) in
                            PriceOfCoinMarket(timestamp: Float(index), price: Double(price) ?? 0.0)
                        })
                    )
                }
        }
        
        
        
        func initPaging(coinId: String) async {
            let transactionStream = tnxPagerUseCase.invoke(coinId: coinId)
            try? await asyncSequence(for: transactionStream).collect { pagingData in
                try? await delegate.submitData(pagingData: pagingData)
            }
        }
        
        func loadNextPage() {
            delegate.loadNextPage()
        }
        
        func subscribeDataChanged() async {
            do {
                for try await _ in asyncSequence(for: delegate.onPagesUpdatedFlow) {
                    self.transactions = delegate.getItems()
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
