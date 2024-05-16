//
//  TransactionDashboardHeader.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/16.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Charts

struct PriceOfCoinMarket: Identifiable {
    var timestamp: Float
    var price: Double
    
    var id: Float { timestamp }
    
    init(timestamp: Float, price: Double) {
        self.timestamp = timestamp
        self.price = price
    }
    
    static var mocks: [PriceOfCoinMarket] = [
        PriceOfCoinMarket(timestamp: 0, price: 8),
        PriceOfCoinMarket(timestamp: 1, price: 1),
        PriceOfCoinMarket(timestamp: 2, price: 2),
        PriceOfCoinMarket(timestamp: 3, price: 5),
        PriceOfCoinMarket(timestamp: 4, price: 3),
        PriceOfCoinMarket(timestamp: 5, price: 8),
        PriceOfCoinMarket(timestamp: 6, price: 2),
    ]
}

struct TransactionDashboard {
    var balance: String
    var symbol: String
    var marketPrices: [PriceOfCoinMarket]
}

struct TransactionDashboardHeader: View {
    
    var dashboard: TransactionDashboard = TransactionDashboard(balance: "0.00", symbol: "ETH", marketPrices: PriceOfCoinMarket.mocks)
    var height: CGFloat = 250
    var lineColor: Color = Color.red
    
    var body: some View {
        Chart(dashboard.marketPrices) {
            LineMark(
                x: .value("", $0.timestamp),
                y: .value("", $0.price)
            ).interpolationMethod(.catmullRom)
                .foregroundStyle(.red)
        }.overlay(
            HStack(spacing: 4) {
                Text(dashboard.balance)
                    .foregroundStyle(.white)
                    .font(.largeTitle)
                    .fontWeight(.bold)
                
                Text(dashboard.symbol)
                    .foregroundStyle(.white)
                    .font(.largeTitle)
                    .fontWeight(.bold)
            }
            .padding(16)
            .frame(maxWidth: .infinity, alignment: .bottomLeading)
            .background(LinearGradient(colors: [Color.clear, .black], startPoint: .top, endPoint: .bottom)), alignment: .bottomLeading
        ).chartXAxis(.hidden)
            .chartYAxis(.hidden)
            .frame(height: height)
    }
}

#Preview {
    ZStack {
        Color.accentColor.ignoresSafeArea()
        ScrollView {
            TransactionDashboardHeader()
        }.ignoresSafeArea()
    }
}
