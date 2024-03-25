//
//  NewsViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/16.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Combine

extension NewsScreen {
    @MainActor final class ViewModel: ObservableObject {
//        private let newsComponent = NewsComponent()
//        @Published private(set) var news:[ModelNews] = []
//        private var offset: Int32 = 0
//        private let limit: Int32 = 20
//        @Published private(set) var hasNotMore: Bool = false
//        
//        private var disposables = Set<AnyCancellable>()
//        
//        init() {
//            loadNews()
//        }
//        
//        func loadMoreNews(currentHash: String) {
//            let lastHash = self.news.last?.hash ?? ""
//            if !hasNotMore && lastHash == currentHash {
//                offset = offset + limit
//                print("load more, \(offset)")
//                loadNews()
//            }
//        }
//        
//        private func loadNews() {
//            createPublisher(wrapper: newsComponent.allNews(limit: limit, offset: offset))
//                .sink(receiveCompletion: { result in switch result {
//                case .finished:
//                    print("load start offset \(self.offset) news finished")
//                case let .failure(throwable):
//                    print("Completed with failure: \(throwable)")
//                }
//                }, receiveValue: { news in
//                    if news == nil || news?.count == 0 {
//                        self.hasNotMore = true
//                    } else {
//                        self.news.append(contentsOf: news as! [ModelNews])
//                    }
//                }).store(in: &disposables)
//        }
//        
//        func onCleared() {
////            self.news = []
////            self.offset = 0
////            self.disposables.forEach { disposable in
////                disposable.cancel()
////            }
//        }
    }
}
