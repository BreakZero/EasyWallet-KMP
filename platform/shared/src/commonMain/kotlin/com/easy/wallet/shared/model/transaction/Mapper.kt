package com.easy.wallet.shared.model.transaction

import com.easy.wallet.core.commom.DateTimeDecoder
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.model.transaction.EthereumTransactionBasic
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal

internal fun EthereumTransactionBasic.asTransactionUiModel(
    token: TokenBasicResult,
    account: String
): EthereumTransactionUiModel {
    val direction = if (sender.equals(account, true)) Direction.SEND else Direction.RECEIVE
    return EthereumTransactionUiModel(
        hash = hash,
        amount = amount.toBigDecimal().moveDecimalPoint(-token.decimals)
            .roundToDigitPositionAfterDecimalPoint(8, RoundingMode.ROUND_HALF_CEILING)
            .toPlainString(),
        recipient = recipient,
        sender = sender,
        direction = direction,
        gasPrice = gasPrice,
        gas = gas,
        gasUsed = gasUsed,
        symbol = token.symbol,
        functionName = functionName,
        datetime = datetime.toLongOrNull()
            ?.let { DateTimeDecoder.decodeToDateTime(it.times(1000)).toString() } ?: "- -",
        status = TransactionStatus.Confirmed
    )
}

internal fun EthereumTransactionBasic.asTransactionUiModel(
    coin: AssetCoin,
    decimalPlace: Int,
    account: String
): EthereumTransactionUiModel {
    val direction = if (sender.equals(account, true)) Direction.SEND else Direction.RECEIVE
    return EthereumTransactionUiModel(
        hash = hash,
        amount = amount.toBigDecimal().moveDecimalPoint(decimalPlace)
            .roundToDigitPositionAfterDecimalPoint(8, RoundingMode.ROUND_HALF_CEILING)
            .toPlainString(),
        recipient = recipient,
        sender = sender,
        direction = direction,
        gasPrice = gasPrice,
        gas = gas,
        gasUsed = gasUsed,
        symbol = coin.symbol,
        functionName = functionName,
        datetime = datetime.toLongOrNull()
            ?.let { DateTimeDecoder.decodeToDateTime(it.times(1000)).toString() } ?: "- -",
        status = TransactionStatus.Confirmed
    )
}

