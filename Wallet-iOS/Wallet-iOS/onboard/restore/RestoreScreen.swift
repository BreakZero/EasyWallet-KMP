////
////  RestoreScreen.swift
////  Wallet-iOS
////
////  Created by Jin on 2023/9/20.
////  Copyright © 2023 orgName. All rights reserved.
////
//
//import SwiftUI
//
//struct RestoreScreen: View {
//    @Environment(\.dismiss) private var dismiss
//    @ObservedObject private var viewModel: ViewModel = ViewModel()
//    var body: some View {
//        VStack {
//            TextField("Seed phrase", text: $viewModel.seedPhrase, axis: .vertical)
//                .textFieldStyle(.easy)
//                .lineLimit(3...6)
//            TextField("New Password", text: $viewModel.password)
//                .textFieldStyle(.easy)
//            TextField("Confirm Password", text: $viewModel.confirmPassword)
//                .textFieldStyle(.easy)
//            Spacer()
//            Button(action: {
//                viewModel.restoreWallet {
//                    dismiss()
//                }
//            }, label: {
//                Text("Import").frame(maxWidth: .infinity).frame(height: 40)
//            }).padding(.horizontal).buttonStyle(.easy)
//        }
//    }
//}
//
//#Preview {
//    RestoreScreen()
//}
