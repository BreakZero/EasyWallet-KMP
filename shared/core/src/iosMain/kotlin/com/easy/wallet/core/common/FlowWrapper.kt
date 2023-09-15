package com.easy.wallet.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "FlowWrapper")
class FlowWrapper<out T> internal constructor(
    private val scope: CoroutineScope,
    private val flow: Flow<T & Any>
) {
    fun subscribe(
        onEach: (T & Any) -> Unit,
        onCompletion: (Throwable?) -> Unit
    ): Job = flow
        .onEach(onEach)
        .onCompletion { throwable: Throwable? -> onCompletion(throwable) }
        .launchIn(scope)
}

fun <T> Flow<T&Any>.wrap(scope: CoroutineScope = MainScope()) = FlowWrapper(scope, this)
