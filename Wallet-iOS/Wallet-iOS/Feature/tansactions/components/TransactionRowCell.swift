//
//  TransactionRowCell.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/16.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import platform_shared

struct TransactionRowCell: View {
    let transaction: TransactionUiModel
    
    var body: some View {
        HStack {
            Text(transaction.direction.name)
            Spacer()
            Text("\(self.transaction.amount) \(self.transaction.symbol)")
        }.padding(.horizontal).padding(.vertical, 8)
    }
}

#Preview {
    TransactionRowCell(
        transaction: EthereumTransactionUiModel(
            hash: "",
            amount: "8.88",
            recipient: "",
            sender: "",
            direction: Direction.receive,
            symbol: "ETH",
            datetime: "",
            status: TransactionStatus.confirmed,
            gas: "",
            gasPrice: "",
            gasUsed: "",
            functionName: nil
        )
    )
}
