//
//  DefaultStyle.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/19.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI


struct EasyDefaultButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding(.vertical, 4)
            .background(Color.primary)
            .foregroundStyle(.white)
            .clipShape(Capsule())
            .scaleEffect(configuration.isPressed ? 1.1 : 1)
            .animation(.easeOut(duration: 0.2), value: configuration.isPressed)
    }
}

extension ButtonStyle where Self == EasyDefaultButtonStyle {
    static var easy: Self { .init() }
}
