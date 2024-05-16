//
//  NewsRowCell.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/16.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import platform_shared

struct NewsRowCell: View {
    let news: ModelNews
    private var tags: [String] = []
    
    init(news: ModelNews) {
        self.news = news
        self.tags = news.tags.components(separatedBy: ",")
    }
    
    var body: some View {
        VStack(spacing: 8) {
            Text(news.title)
                .lineLimit(2)
                .font(.title)
                .fontWeight(.bold)
                .frame(maxWidth: .infinity, alignment: .leading)
            Text(news.description_)
                .lineLimit(3)
                .font(.body)
                .fontWeight(.regular)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            HStack(spacing: 8) {
                let tagCount = tags.count > 3 ? 3 : tags.count
                ForEach(0..<tagCount, id: \.id) {
                    Text(tags[$0])
                        .font(.custom("", size: 12))
                        .fontWeight(.thin)
                        .padding(4)
                        .background(Color.accentColor.opacity(0.5))
                        .cornerRadius(4.0)
                }
            }.frame(maxWidth: .infinity, alignment: .leading)
            
        }
    }
}

#Preview {
    NewsRowCell(
        news: ModelNews(
            title: "Sample title",
            source: "",
            language: "en",
            link: "",
            time: "2024.0516",
            hash: "mock_hash",
            description: "sample description",
            tags: "Bitcoin, News"
        )
    )
}
