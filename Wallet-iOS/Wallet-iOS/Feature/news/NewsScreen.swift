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
                    VStack {
                        ProgressView(label: { Text("Loading...") })
                    }
                }
                
                
                if (!viewModel.newsResult.isEmpty) {
                    VStack(alignment: .center) {
                        if(viewModel.hasNextPage) {
                            ProgressView().onAppear {
                                viewModel.loadNextPage()
                            }
                        } else {
                            Text("-- Not more --").foregroundColor(.gray)
                        }
                    }.frame(maxWidth: .infinity).listRowInsets(EdgeInsets())
                        .listRowBackground(Color.clear)
                }
                
            }.listStyle(.plain)
                .toolbar {
                    ToolbarItem(placement: .navigationBarLeading) {
                        Text("Powered by BlockChair")
                    }
                }
        }.onFirstAppear {
            viewModel.startLoadNews()
        }.task {
            await viewModel.subscribeDataChanged()
        }.task {
            await viewModel.subscribeLoadState()
        }
    }
}
