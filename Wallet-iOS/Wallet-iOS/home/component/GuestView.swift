//
//  GuestView.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/19.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GuestView: View {
    var body: some View {
        VStack {
            Image("wallet_setup")
            
            Text("Wallet Setup")
            
            Text("Import an existing wallet \n or create a new one")
            Spacer()
            Button(action: {}, label: {
                Text("Create wallet").frame(maxWidth: .infinity)
                    .frame(height: 40)
            }).padding(.horizontal).buttonStyle(.easy)
            Button(action: {}, label: {
                Text("I already have one").frame(maxWidth: .infinity)
                    .frame(height: 40)
            }).padding(.horizontal)
                .buttonStyle(.easy)
        }
    }
}

#Preview {
    GuestView()
}
