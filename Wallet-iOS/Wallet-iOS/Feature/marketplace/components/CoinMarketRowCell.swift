//
//  CoinMarketRowCell.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/21.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CoinMarketRowCell: View {
    
    var coinLogoUri:String = Constants.randomImage
    var coinName: String = "Bitcoin"
    var coinSymbol: String = "BTC"
    var coinPrice: String = "71257.02"
    var percent: String  = "7.139 %"
    
    var body: some View {
        HStack {
            ImageLoaderView(urlString: coinLogoUri)
                .cornerRadius(48)
                .frame(width: 48, height: 48)
            VStack(alignment: .leading) {
                Text(coinName)
                Text(coinSymbol).font(.callout)
            }
            Spacer()
            VStack(alignment: .trailing) {
                Text(coinPrice)
                Text(percent)
            }
        }.padding()
    }
}

#Preview {
    CoinMarketRowCell()
}
