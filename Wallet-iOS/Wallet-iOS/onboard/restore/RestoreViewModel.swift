//
//  RestoreViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/20.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation


extension RestoreScreen {
    @MainActor final class ViewModel: ObservableObject {
        @Published var seedPhrase: String = ""
        @Published var password: String = ""
        @Published var confirmPassword: String = ""
        
        
    }
}
