//
//  AsyncExtensions.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/26.
//  Copyright © 2024 orgName. All rights reserved.
//  copy from https://github.com/sideeffect-io/AsyncExtensions/blob/main/Sources/Operators/AsyncSequence%2BCollect.swift
//

import Foundation

public extension AsyncSequence {
    /// Iterates over each element of the AsyncSequence and give it to the block.
    ///
    /// ```
    /// let sequence = AsyncLazySequence([1, 2, 3])
    /// sequence.collect { print($0) } // will print 1 2 3
    /// ```
    ///
    /// - Parameter block: The closure to execute on each element of the async sequence.
    func collect(_ block: (Element) async throws -> Void) async rethrows {
        for try await element in self {
            try await block(element)
        }
    }
    
    /// Iterates over each element of the AsyncSequence and returns elements in an Array.
    ///
    /// ```
    /// let sequence = AsyncLazySequence([1, 2, 3])
    /// let origin = sequence.collect()
    /// // origin contains 1 2 3
    /// ```
    ///
    /// - Returns: the array of elements produced by the AsyncSequence
    func collect() async rethrows -> [Element] {
        var elements = [Element]()
        for try await element in self {
            elements.append(element)
        }
        return elements
    }
}
