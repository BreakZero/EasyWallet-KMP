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
    @State private(set) var transaction: ModelTransaction
    private var isSend: Bool = false
    init(transaction: ModelTransaction) {
        self.transaction = transaction
        isSend = transaction.direction == ModelDirection.send
    }
    
    var body: some View {
        HStack {
            Text(isSend ? "Send":"Receive")
            Spacer()
            Text("\(self.transaction.amount) \(self.transaction.symbol)")
        }.padding(.horizontal).padding(.vertical, 8)
    }
}

//#Preview {
//    TransactionSummaryView(
//        transaction: Ethereum
//    )
//}
