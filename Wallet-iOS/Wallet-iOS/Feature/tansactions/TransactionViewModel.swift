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

        private var delegate = PagingCollectionViewController<TransactionUiModel>()

        @LazyKoin private var tokenAmountUseCase: TokenBalanceUseCase
        @LazyKoin private var coinTrendUseCase: CoinTrendUseCase
        @LazyKoin private var tnxPagerUseCase: TransactionPagerUseCase

        @Published private(set) var transactions:[TransactionUiModel] = []
        @Published private(set) var hasNextPage: Bool = false
        @Published private(set) var showLoading: Bool = false

        @Published private(set) var dashboardDesc: String = ""

        func loading(tokenId: String) async {
            try? await combineLatest(tokenAmountUseCase.invoke(tokenId: tokenId), coinTrendUseCase.invoke(tokenId: tokenId)).collect { balance, trends in
                print("====== \(balance), trends: \(trends)")
                self.dashboardDesc = "====== \(balance), trends: \(trends)"
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
                    self.showLoading = true
                    break
                case .notLoading(_):
                    self.showLoading = false
                    break
                }
            }
        }
    }
}
