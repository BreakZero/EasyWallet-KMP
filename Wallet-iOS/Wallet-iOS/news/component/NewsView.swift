//
//  NewsItemView.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/16.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import data

struct NewsView: View {
    private let news: ModelNews
    
    init(news: ModelNews) {
        self.news = news
    }
    
    var body: some View {
        VStack {
            Text(news.title)
                .font(.title2)
                .frame(maxWidth: .infinity, alignment: .leading)
            Text(news.description_)
                .lineLimit(2)
                .font(.body)
                .frame(maxWidth: .infinity, alignment: .leading)
            Text(news.source)
                .font(.footnote)
                .frame(maxWidth: .infinity, alignment: .leading)
        }
    }
}
