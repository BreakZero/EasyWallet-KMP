//
//  CoinRowCell.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/16.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import platform_shared

var mockAssetBalance: ModelAssetBalance = ModelAssetBalance(
    id: "ethereum_eth",
    name: "Ethereum",
    symbol: "ETH",
    decimalPlace: 18,
    logoURI: "https://assets.coingecko.com/coins/images/279/small/ethereum.png",
    contract: nil,
    platform: ModelAssetPlatform(
        id: "ethereum",
        shortName: "Ethereum",
        chainIdentifier: "1",
        network: nil
    ),
    address: "0x81080a7e991bcDdDBA8C2302A70f45d6Bd369Ab5",
    balance: "8.88"
)

struct CoinRowCell: View {
    var logoSize: CGFloat = 50
    var assetBalance: ModelAssetBalance = mockAssetBalance
    
    var body: some View {
        HStack(spacing: 12) {
            ImageLoaderView(urlString: assetBalance.logoURI)
                .frame(width: logoSize, height: logoSize)
                .background(Color.accentColor)
                .clipShape(Circle())
            
            Text(assetBalance.name)
                .font(.body)
                .fontWeight(.medium)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Text(assetBalance.balance)
                .font(.body)
                .fontWeight(.bold)
            
            Text(assetBalance.symbol)
                .font(.body)
                .fontWeight(.regular)
        }
    }
}

#Preview {
    CoinRowCell().frame(height: 100)
}
