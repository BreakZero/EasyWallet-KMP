//
//  TrendCoinRowCell.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/21.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TrendCoinRowCell: View {
    var coinLogoUri: String = Constants.randomImage
    var coinName: String = "Bitcoin(NORMIE)"
    
    var body: some View {
        VStack(spacing: 8) {
            ImageLoaderView(urlString: coinLogoUri)
                .cornerRadius(48)
                .clipped()
                .frame(width: 48, height: 48)
            Text(coinName)
        }.padding().background(Color.blue).cornerRadius(8)
    }
}

#Preview {
    TrendCoinRowCell()
}
