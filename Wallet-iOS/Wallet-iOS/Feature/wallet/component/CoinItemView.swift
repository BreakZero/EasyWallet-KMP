//
//  TokenItemView.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/15.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import platform_shared

struct CoinItemView: View {
    
    private let assetBalance: ModelAssetBalance

    init(assetBalance: ModelAssetBalance) {
        self.assetBalance = assetBalance
    }

    var body: some View {
        HStack {
            AsyncImage(url: URL(string: assetBalance.logoURI)) { phase in switch phase {
            case .failure: Image(systemName: "photo").font(.largeTitle)
            case .success(let image): image.resizable()
                default: ProgressView() }
            }.frame(width: 48, height: 48)
                .clipShape(Circle())

            Text(assetBalance.name)
            Spacer()
            Text("\(assetBalance.balance) \(assetBalance.symbol)")
        }
    }
}
