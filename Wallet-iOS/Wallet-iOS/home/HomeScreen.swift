//
//  HomeScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import data

struct HomeScreen: View {
    @ObservedObject private var viewModel = HomeViewModel()
    var body: some View {
        VStack {
            List(viewModel.tokens, id: \.self.token.id) { token in
                TokenItemView(extraToken: token)
            }
        }.onDisappear {
            viewModel.onCleared()
        }
    }
}

#Preview {
    Text("HELLO ")
}
