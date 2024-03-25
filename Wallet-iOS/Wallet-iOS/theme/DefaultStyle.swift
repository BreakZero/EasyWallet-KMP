//
//  DefaultStyle.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/19.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct EasyDefaultTextFieldStyle: TextFieldStyle {
    func _body(configuration: TextField<Self._Label>) -> some View {
        configuration
            .padding(.all)
            .overlay {
                RoundedRectangle(cornerRadius: 12)
                    .stroke(Color.primary.opacity(0.5), lineWidth: 2)
            }
            .padding(.horizontal)
        //            .shadow(color: .gray, radius: 10)
    }
}

extension TextFieldStyle where Self == EasyDefaultTextFieldStyle {
    static var easy: Self {.init()}
}

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
