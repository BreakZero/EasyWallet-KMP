//
//  TokenItemView.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/15.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import data

struct TokenItemView: View {
    private let token: ModelToken
    
    init(token: ModelToken) {
        self.token = token
    }
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: token.logoURI)) { phase in switch phase {
            case .failure: Image(systemName: "photo").font(.largeTitle)
            case .success(let image): image.resizable()
                default: ProgressView() }
            }.frame(width: 48, height: 48)
                .clipShape(Circle())
            
            Text(token.name)
        }
    }
}

#Preview {
     TokenItemView(
        token: ModelToken(
            id: "1",
            name: "Ethereum",
            symbol: "ETH",
            decimals: 18,
            type: ModelCoinValsCoinType.coin,
            address: "",
            logoURI: "https://hws.dev/paul.jpg"
        )
    )
}
