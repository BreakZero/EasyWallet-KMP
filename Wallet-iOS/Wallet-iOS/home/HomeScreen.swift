//
//  HomeScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/8/24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct HomeScreen: View {
    @ObservedObject private var viewModel = HomeViewModel()
    var body: some View {
        Text("Home")
    }
}

#Preview {
    HomeScreen()
}
