//
//  TransactionItemView.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/27.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TransactionSummaryView: View {
    @State private(set) var transaction: TransactionUiModel
    private var action: String
    init(transaction: TransactionUiModel) {
        self.transaction = transaction
        
        action = switch transaction.direction {
        case .receive:
            "Receive"
        case .send:
            "Send"
        }
    }
    
    var body: some View {
        HStack {
            Text(action)
            Spacer()
            Text("\(self.transaction.amount) \(self.transaction.symbol)")
        }.padding(.horizontal).padding(.vertical, 8)
    }
}
