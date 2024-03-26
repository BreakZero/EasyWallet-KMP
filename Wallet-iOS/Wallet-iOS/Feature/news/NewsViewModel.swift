//
//  NewsViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/16.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NewsScreen {
    @MainActor final class ViewModel: ObservableObject {
        
        @LazyKoin private var newsPager: NewsPager
        
        private var delegate = PagingCollectionViewController<ModelNews>()
        
        @Published private(set) var newsResult:[ModelNews] = []
        @Published private(set) var hasNextPage: Bool = false
        @Published private(set) var showLoding: Bool = false

        func startLoadNews() async {
            let newsStream = newsPager.invoke().flow
            await newsStream.collect { pagingData in
                print(pagingData.description)
                try? await skie(delegate).submitData(pagingData: pagingData)
            }
        }
        
        func subscribeDataChanged() async {
            for await _ in delegate.onPagesUpdatedFlow {
                self.newsResult = delegate.getItems()
            }
        }
        
        func loadNextPage() {
            delegate.loadNextPage()
        }
        
        func subscribeLoadState() async {
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
