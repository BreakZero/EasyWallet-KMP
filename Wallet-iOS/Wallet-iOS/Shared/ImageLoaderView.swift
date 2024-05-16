//
//  ImageLoaderView.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/5/16.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ImageLoaderView: View {
    var urlString: String = ""
    var resizingMode: ContentMode = .fill
    
    var body: some View {
        Rectangle().opacity(0.001)
            .overlay {
                AsyncImage(url: URL(string: urlString)) { phase in switch phase {
                case .failure: Image(systemName: "photo.fill.on.rectangle.fill").resizable().aspectRatio(contentMode: resizingMode).font(.largeTitle)
                case .success(let image): image.resizable().aspectRatio(contentMode: resizingMode)
                default: ProgressView()
                }
                }.allowsHitTesting(false)
            }.clipped()
       
    }
}

#Preview {
    ImageLoaderView(urlString: "https://picsum.photos/600")
        .cornerRadius(30)
        .padding(40)
        .padding(.vertical, 60)
}
