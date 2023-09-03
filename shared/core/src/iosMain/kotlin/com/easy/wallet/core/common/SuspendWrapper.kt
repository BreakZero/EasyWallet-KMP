package com.easy.wallet.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "SuspendWrapper")
class SuspendWrapper<out T> internal constructor(
    private val scope: CoroutineScope,
    private val block: suspend () -> T & Any
) {
    private var job: Job? = null
    private var isCancelled: Boolean = false

    fun cancel() {
        isCancelled = true
        job?.cancel()
    }

    suspend fun execute(): T & Any {
        val deferred = scope.async(start = CoroutineStart.LAZY) { block() }
        job = deferred
        if (isCancelled) deferred.cancel() else deferred.start()
        return deferred.await()
    }
}