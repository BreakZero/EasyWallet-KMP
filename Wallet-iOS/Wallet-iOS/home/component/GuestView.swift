//
//  GuestView.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/19.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GuestView: View {
    @State private var showActions: Bool = false
    @State private var isForCreate: Bool = true
    
    var body: some View {
        GeometryReader { geometry in
            VStack {
                Spacer()
                Image("wallet_setup").resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: geometry.size.width * 0.7)
                
                Text("Wallet Setup").font(.title).frame(maxWidth: .infinity, alignment: .leading).padding(.horizontal)
                
                Text("Import an existing wallet \n or create a new one").frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.horizontal)
                Spacer()
                Button(action: {
                    self.isForCreate = true
                    showActions = true
                }, label: {
                    Text("Create wallet").frame(maxWidth: .infinity)
                        .frame(height: 40)
                }).padding(.horizontal).buttonStyle(.easy)
                Button(action: {
                    self.isForCreate = false
                    showActions = true
                }, label: {
                    Text("I already have one").frame(maxWidth: .infinity)
                        .frame(height: 40)
                }).padding(.horizontal)
                    .buttonStyle(.easy)
            }.sheet(isPresented: $showActions) {
                ActionSheetView(isForCreate: self.isForCreate)
            }
        }
    }
}

#Preview {
    GuestView()
}
