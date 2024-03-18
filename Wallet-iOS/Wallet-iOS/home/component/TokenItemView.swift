//
//  TokenItemView.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/15.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TokenItemView: View {
    private let token: ModelToken
    private let balance: Balance

    init(extraToken: ExtraToken) {
        self.token = extraToken.token
        self.balance = extraToken.balance
    }

    var body: some View {
        HStack {
            AsyncImage(url: URL(string: token.iconUri)) { phase in switch phase {
            case .failure: Image(systemName: "photo").font(.largeTitle)
            case .success(let image): image.resizable()
                default: ProgressView() }
            }.frame(width: 48, height: 48)
                .clipShape(Circle())

            Text(token.name)
            Spacer()
            Text("\(balance.approximate(scale: 8)) \(token.symbol)")
        }
    }
}

#Preview {
     TokenItemView(
        extraToken: ExtraToken(token: ModelToken(
            id: "1",
            name: "Ethereum",
            symbol: "ETH",
            decimals: 18,
            type: ModelCoinValsCoinType.coin,
            address: "",
            logoURI: "https://hws.dev/paul.jpg"
        ), balance: Balance.companion.ZERO)
    )
}
