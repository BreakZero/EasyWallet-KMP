//
//  NewsScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/16.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NewsScreen: View {
    @ObservedObject private var viewModel = ViewModel()
    
    var body: some View {
        NavigationStack {
            List {
                ForEach(viewModel.newsResult, id: \.self.title) { news in
                    Button(action: {
                        if let url = URL(string: news.link), UIApplication.shared.canOpenURL(url) {
                            UIApplication.shared.open(url)
                        }
                    }, label: {
                        NewsView(news: news)
                            .listRowInsets(EdgeInsets())
                            .listRowBackground(Color.clear)
                    })
                }
                
                if viewModel.showLoding {
                    // loading view
                }
                
                if (viewModel.hasNextPage && !viewModel.newsResult.isEmpty) {
                    VStack {
                        ProgressView()
                    }.onAppear {
                        viewModel.loadNextPage()
                    }
                }
                
                if (!viewModel.hasNextPage && !viewModel.newsResult.isEmpty) {
                    VStack {
                        Text("-- Not more --")
                    }
                }
            }.listStyle(.plain)
                .toolbar {
                    ToolbarItem(placement: .navigationBarLeading) {
                        Text("Powered by BlockChair")
                    }
                }
        }.task {
            await viewModel.startLoadNews()
        }.task {
            await viewModel.subscribeDataChanged()
        }.task {
            await viewModel.subscribeLoadState()
        }
    }
}
