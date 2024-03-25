////
////  RestoreViewModel.swift
////  Wallet-iOS
////
////  Created by Jin on 2023/9/20.
////  Copyright © 2023 orgName. All rights reserved.
////
//
//import Foundation
//import shared
//
//
//extension RestoreScreen {
//    @MainActor final class ViewModel: ObservableObject {
//        private let multiWalletRepository =  MultiWalletComponent()
//        private var insertTask: Task<Void, Never>? = nil
//        
//        @Published var seedPhrase: String = ""
//        @Published var password: String = ""
//        @Published var confirmPassword: String = ""
//        
//        func restoreWallet(
//            onCompletion: @escaping () -> Void
//        ) {
//            insertTask = Task {
//                do {
//                    _ = try await suspend(multiWalletRepository.insertOne(mnemonic: seedPhrase))
//                    onCompletion()
//                } catch {
//                    debugPrint("we get an error \(error)")
//                }
//            }
//        }
//        func onCleaned() {
//            insertTask?.cancel()
//            insertTask = nil
//        }
//    }
//}
