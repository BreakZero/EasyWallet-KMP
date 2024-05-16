//
//  CoinListHeaderCell.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/16.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import SwiftfulUI

struct CoinListHeaderCell: View {
    var height: CGFloat = 300
    
    var body: some View {
        Rectangle()
            .opacity(0.001)
            .overlay(
                HStack(spacing: 88) {
                    Image(systemName: "arrow.up")
                        .resizable()
                        .padding(12)
                        .background(Color.accentColor.opacity(0.7))
                        .clipShape(Circle())
                        .clipped()
                        .frame(width: 50, height: 50)
                    Image(systemName: "arrow.down")
                        .resizable()
                        .padding(12)
                        .background(Color.accentColor.opacity(0.7))
                        .clipShape(Circle())
                        .clipped()
                        .frame(width: 50, height: 50)
                }
            ).frame(height: height)
    }
}

#Preview {
    ZStack {
        Color.black.ignoresSafeArea()
        ScrollView {
            CoinListHeaderCell()
        }.ignoresSafeArea()
    }
}
