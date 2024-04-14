//
//  OnFirstAppearModifier.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/4/10.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

public struct OnFirstAppearModifier: ViewModifier {

    private let onFirstAppearAction: () -> ()
    @State private var hasAppeared = false
    
    public init(_ onFirstAppearAction: @escaping () -> ()) {
        self.onFirstAppearAction = onFirstAppearAction
    }
    
    public func body(content: Content) -> some View {
        content
            .onAppear {
                guard !hasAppeared else { return }
                hasAppeared = true
                onFirstAppearAction()
            }
    }
}

extension View {
    func onFirstAppear(_ onFirstAppearAction: @escaping () -> () ) -> some View {
        return modifier(OnFirstAppearModifier(onFirstAppearAction))
    }
}
