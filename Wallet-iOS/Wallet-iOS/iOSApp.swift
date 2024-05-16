import SwiftUI
import SwiftfulRouting

@main
struct iOSApp: App {
    init() {
        KoinApplication.start()
    }
    var body: some Scene {
        WindowGroup {
            
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
}
