package com.easy.wallet.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

internal val mainScope: CoroutineScope = MainScope()

fun <T> Flow<T>.subscribe(
    onEach: (item: T) -> Unit,
    onCompleted: () -> Unit,
    onError: (error: Throwable) -> Unit
): Job {
    return this
        .onEach { onEach(it) }
        .catch { onError(it) }
        .onCompletion { onCompleted() }
        .launchIn(mainScope)
}

fun <T : Any> (suspend () -> T).wrap(scope: CoroutineScope = mainScope): SuspendWrapper<T> {
    return SuspendWrapper(scope = scope, block = this)
}
