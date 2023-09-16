//
//  NewsScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/16.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import data

struct NewsScreen: View {
    @ObservedObject private var viewModel = ViewModel()
    
    var body: some View {
        VStack {
            List(viewModel.news, id: \.self.title) { news in
                Text(news.title).onAppear {
                    viewModel.loadMoreNews(currentHash: news.hash)
                }
            }
        }
    }
}
