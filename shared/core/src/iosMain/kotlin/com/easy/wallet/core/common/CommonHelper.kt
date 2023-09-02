package com.easy.wallet.core.common

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIImage

class CommonHelper {
    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    fun getUIImageFromBytes(bytes: ByteArray): UIImage {
        val data = bytes.usePinned {
            NSData.create(
                bytes = it.addressOf(0),
                length = bytes.size.toULong()
            )
        }
        return UIImage(data)
    }
}