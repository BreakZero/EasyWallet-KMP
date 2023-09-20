//
//  ActionSheetView.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/20.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ActionSheetItem {
    let title: String
    let desc: String
    let trailingIcon: UIImage
}

let CreateActions = [
    ActionSheetItem(title: "Seed phrase", desc: "Create a wallet by generating seed phrase", trailingIcon: UIImage(systemName: "") ?? UIImage.actions)
]

let RestoreActions = [
    ActionSheetItem(title: "Seed phrase", desc: "12,18,24-word seed phrases are supported", trailingIcon: UIImage(systemName: "") ?? UIImage.add)
]

struct ActionSheetView: View {
    private let actions: [ActionSheetItem]
    
    init(isForCreate: Bool) {
        if isForCreate {
            self.actions = CreateActions
        } else {
            self.actions = RestoreActions
        }
    }
    var body: some View {
        NavigationView {
            List(actions, id: \.self.title) { actionItem in
                NavigationLink(destination: Text(actionItem.desc), label: {
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
            }
        }
    }
}

#Preview {
    ActionSheetView(isForCreate: false)
}
