//
//  RootEntryView.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/16.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RootEntryView: View {
    var body: some View {
        TabView {
            WalletTabView().tabItem {
                Label(
                    title: { Text("Wallet") },
                    icon: { Image(systemName: "house.fill") }
                )
            }
            NewsScreen().tabItem {
                Label(
                    title: { Text("News")},
                    icon: { Image(systemName: "newspaper") }
                )
            }
            MarketplaceScreen().tabItem {
                Label(
                    title: { Text("Marketplace") },
                    icon: { Image(systemName: "chart.line.uptrend.xyaxis") }
                )
            }
            DiscoverScreen().tabItem {
                Label(
                    title: { Text("Discover") },
                    icon: { Image(systemName: "circle.grid.cross.down.filled") }
                )
            }
        }
    }
}

#Preview {
    RootEntryView()
}
