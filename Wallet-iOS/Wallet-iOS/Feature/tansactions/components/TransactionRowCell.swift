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
        VStack(spacing:12) {
            Text(transaction.datetime).font(.body).fontWeight(.regular).frame(maxWidth: .infinity, alignment: .leading)
            HStack {
                Image(systemName: transaction.direction == Direction.send ? "arrow.up.circle" : "arrow.down.circle").resizable().padding(8).frame(width: 50, height: 50)
                VStack {
                    Text(actionTitle()).font(.title3).fontWeight(.regular).frame(maxWidth: .infinity, alignment: .leading)
                    Text(transaction.status.name)
                        .font(.body)
                        .fontWeight(.regular)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }.frame(maxWidth: .infinity)
                Text("\(self.transaction.amount) \(self.transaction.symbol)")
                    .font(.title2)
                    .fontWeight(.bold)
            }
        }.padding(.horizontal).padding(.vertical, 8)
    }
    
    private func actionTitle() -> String {
        let isSend = transaction.direction == Direction.send
        let methodName = (transaction as? EthereumTransactionUiModel)?.functionName?.components(separatedBy: "(")[0]
        if methodName?.isEmpty != nil {
            return isSend ? "Send \(transaction.symbol)" : "Receive \(transaction.symbol)"
        } else {
            return methodName ?? (isSend ? "Send \(transaction.symbol)" : "Receive \(transaction.symbol)")
        }
    }
}

#Preview {
    VStack {
        TransactionRowCell(
            transaction: EthereumTransactionUiModel(
                hash: "",
                amount: "8.88",
                recipient: "",
                sender: "",
                direction: Direction.receive,
                symbol: "ETH",
                datetime: "2023.08.23",
                status: TransactionStatus.confirmed,
                gas: "",
                gasPrice: "",
                gasUsed: "",
                functionName: nil
            )
        )
    }
}
