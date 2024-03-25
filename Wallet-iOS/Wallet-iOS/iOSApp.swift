import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        KoinApplication.start()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
