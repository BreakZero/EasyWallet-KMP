//
//  FlowWrapperHelper.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/15.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Combine
import data

class SharedError: LocalizedError {
    let throwable: KotlinThrowable
    init(_ throwable: KotlinThrowable) {
        self.throwable = throwable
    }
    var errorDescription: String? {
        get { throwable.message }
    }
}

func createPublisher<T>(wrapper: FlowWrapper<T>) -> AnyPublisher<T?, Error> {
    let subject = PassthroughSubject<T?, Error>()
    var job: Kotlinx_coroutines_coreJob? = nil
    return subject.handleEvents(
    receiveSubscription: { subscription in
        print("subscribe...")
        job = wrapper.subscribe(
            onEach: { item in subject.send(item) },
            onCompletion: { _ in subject.send(completion: .finished) },
            onThrow: { error in subject.send(completion: .failure(SharedError(error))) }
        )
    },
    receiveCancel: {
        print("cancel...")
        job?.cancel(cause: nil)
    }
    ).eraseToAnyPublisher()
}
