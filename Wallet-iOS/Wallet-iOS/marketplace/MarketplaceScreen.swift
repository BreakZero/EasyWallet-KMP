//
//  MarketplaceScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import SwiftUICharts

struct MarketplaceScreen: View {
    var demoData: [Double] = [8,2,4,6,12,9,2]
    var body: some View {
        LineChart().data(demoData)
            .chartStyle(ChartStyle(
                backgroundColor: .white,
                foregroundColor: ColorGradient(.blue, .purple))
            )
    }
}

#Preview {
    MarketplaceScreen()
}
