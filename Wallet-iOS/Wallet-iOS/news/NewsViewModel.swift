//
//  NewsViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/16.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import AsyncExtensions

extension NewsScreen {
    @MainActor final class ViewModel: ObservableObject {
        
        @LazyKoin private var newsRepository: NewsRepository
        
        @Published private(set) var newsResult:[ModelNews] = []
        private var offset: Int32 = 0
        private let limit: Int32 = 20
        @Published private(set) var hasNotMore: Bool = false

        func loadNews() async {
            let newsSteam = newsRepository.loadNewsStream(limit: limit, offset: offset)
            await newsSteam.collect { news in
                self.newsResult.append(contentsOf: news)
            }
        }
    }
}
