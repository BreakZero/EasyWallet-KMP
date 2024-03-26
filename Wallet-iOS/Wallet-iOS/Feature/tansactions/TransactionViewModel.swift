//
//  TransactionViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/25.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import AsyncAlgorithms

extension TransactionScreen {
    @MainActor final class ViewModel: ObservableObject {
        
        private var delegate = PagingCollectionViewController<ModelTransaction>()
        
        @LazyKoin private var tokenAmountUseCase: TokenAmountUseCase
        @LazyKoin private var coinTrendUseCase: CoinTrendUseCase
        @LazyKoin private var tnxPagerUseCase: TransactionPagerUseCase
        
        @Published private(set) var transactions:[ModelTransaction] = []
        @Published private(set) var hasNextPage: Bool = false
        @Published private(set) var showLoding: Bool = false
        
        func loading(tokenId: String) async {
            await combineLatest(tokenAmountUseCase.invoke(tokenId: tokenId), coinTrendUseCase.invoke(tokenId: tokenId)).collect { balance, trends in
                print("====== \(balance), trends: \(trends)")
            }
        }
        
        func initPaging(tokenId: String) async {
            let transactionStream = tnxPagerUseCase.invoke(tokenId: tokenId)
            await transactionStream.collect { pagingData in
                print(pagingData.description)
                try? await skie(delegate).submitData(pagingData: pagingData)
            }
        }
        
        func loadNextPage() {
            delegate.loadNextPage()
        }
        
        func subscribeDataChanged() async {
            for await _ in delegate.onPagesUpdatedFlow {
                self.transactions = delegate.getItems()
            }
        }
        
        func subscribeLoadStateChanged() async {
            for await loadState in delegate.loadStateFlow {
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
                    self.showLoding = true
                    break
                case .notLoading(_):
                    self.showLoding = false
                    break
                }
            }
        }
    }
}
