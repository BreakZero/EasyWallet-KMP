//
//  ActionSheetView.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/20.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

enum ActionType {
    case restore, creation
}

struct ActionSheetItem {
    let type: ActionType
    let title: String
    let desc: String
    let trailingIcon: UIImage
}

let CreateActions = [
    ActionSheetItem(type: ActionType.creation, title: "Seed phrase", desc: "Create a wallet by generating seed phrase", trailingIcon: UIImage(systemName: "") ?? UIImage.actions)
]

let RestoreActions = [
    ActionSheetItem(type: ActionType.restore, title: "Seed phrase", desc: "12,18,24-word seed phrases are supported", trailingIcon: UIImage(systemName: "") ?? UIImage.add)
]

struct ActionSheetView: View {
    private let actions: [ActionSheetItem]
    
    @State private var detent: PresentationDetent = .large
    @State private var title: String = "Choose Action"
    
    init(isForCreate: Bool) {
        if isForCreate {
            self.actions = CreateActions
        } else {
            self.actions = RestoreActions
        }
    }
    var body: some View {
        NavigationStack {
            List(actions, id: \.self.title) { actionItem in
                NavigationLink(destination: {
                    switch(actionItem.type) {
                    case .creation:
                        Text("Sorry, Coming soon(WIP on iOS)").onAppear {
                            detent = .large
                            title = "Create New Wallet"
                        }
                    case .restore:
                        RestoreScreen().onAppear {
                            detent = .large
                            title = "Restore"
                        }
                    }
                    
                }, label: {
                    HStack {
                        VStack {
                            Text(actionItem.title)
                                .font(.title)
                                .frame(maxWidth: .infinity, alignment: .leading)
                            Text(actionItem.desc)
                                .font(.body)
                                .frame(maxWidth: .infinity, alignment: .leading)
                        }
                        Spacer()
                        Image(uiImage: actionItem.trailingIcon)
                    }
                })
            }.navigationBarTitleDisplayMode(.inline)
                .navigationTitle(title).onAppear {
                    detent = .medium
                    title = "Choose Action"
                }
        }.presentationDetents([detent])
    }
}

#Preview {
    ActionSheetView(isForCreate: false)
}
