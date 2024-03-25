//
//  RestoreScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/20.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct RestoreScreen: View {
    @Environment(\.dismiss) private var dismiss
    @ObservedObject private var viewModel: ViewModel = ViewModel()
    
    var body: some View {
        VStack {
            TextField("Seed phrase", text: $viewModel.seedPhrase, axis: .vertical)
                .padding()
                .background(Color.gray.opacity(0.3).cornerRadius(10))
                .lineLimit(3...6)
            TextField("New Password", text: $viewModel.password)
                .padding()
                .background(Color.gray.opacity(0.3).cornerRadius(10))
            TextField("Confirm Password", text: $viewModel.confirmPassword)
                .padding()
                .background(Color.gray.opacity(0.3).cornerRadius(10))
            Spacer()
            Button(action: {
                viewModel.restoreWallet {
                    dismiss()
                }
            }, label: {
                Text("Import").frame(maxWidth: .infinity).frame(height: 40)
            }).buttonStyle(.easy)
        }.padding()
    }
}

#Preview {
    RestoreScreen()
}
