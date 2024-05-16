import SwiftUI
import SwiftfulRouting

@main
struct iOSApp: App {
    init() {
        KoinApplication.start()
    }
    var body: some Scene {
        WindowGroup {
            RootEntryView()
        }
    }
}
