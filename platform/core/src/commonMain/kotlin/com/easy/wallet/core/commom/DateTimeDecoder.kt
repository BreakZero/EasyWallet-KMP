package com.easy.wallet.core.commom

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeDecoder {
  val defaultTimeZone = TimeZone.currentSystemDefault()

  fun decodeToDateTime(timestamp: Long): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    return instant.toLocalDateTime(defaultTimeZone)
  }

  fun encodeToLong(dateTime: LocalDateTime): Long = dateTime.toInstant(defaultTimeZone).toEpochMilliseconds()
}

fun systemCurrentMilliseconds(): Long {
  val currentMoment: Instant = Clock.System.now()
  return currentMoment.toEpochMilliseconds()
}
