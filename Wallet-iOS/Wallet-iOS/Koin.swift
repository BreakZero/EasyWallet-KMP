//
//  Koin.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/24.
//  Copyright © 2024 orgName. All rights reserved.
//
//  Copy from https://github.com/uwaisalqadri/MangaKu/blob/main/mangaku-ios/MangaKu/App/Main/Koin.swift
//

import Foundation
import shared

typealias KoinApplication = Koin_coreKoinApplication
typealias Koin = Koin_coreKoin

extension KoinApplication {
    static let shared = companion.start()
    
    @discardableResult
    static func start() -> KoinApplication {
        shared
    }
}

extension KoinApplication {
    private static let keyPaths: [PartialKeyPath<Koin>] = [
        \.coinTrendUseCase,
        \.dashboardUseCase,
        \.tokenAmountUseCase,
        \.transactionPagerUseCase,
        \.multiWalletRepository,
        \.newsPager
    ]
    
    static func inject<T>() -> T {
        shared.inject()
    }
    
    func inject<T>() -> T {
        for partialKeyPath in Self.keyPaths {
            guard let keyPath = partialKeyPath as? KeyPath<Koin, T> else { continue }
            return koin[keyPath: keyPath]
        }
        
        fatalError("\(T.self) is not registered with KoinApplication")
    }
}

@propertyWrapper
struct LazyKoin<T> {
    lazy var wrappedValue: T = { KoinApplication.shared.inject() }()
    
    init() {}
}
