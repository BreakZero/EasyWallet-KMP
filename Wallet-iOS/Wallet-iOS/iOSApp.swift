import SwiftUI
import data

@main
struct iOSApp: App {
    init() {
        IOSDiHelperKt.doInitKoin()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
