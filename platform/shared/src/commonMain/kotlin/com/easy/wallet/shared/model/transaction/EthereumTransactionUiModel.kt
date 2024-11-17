package com.easy.wallet.shared.model.transaction

data class EthereumTransactionUiModel(
  override val hash: String,
  override val amount: String,
  override val recipient: String,
  override val sender: String,
  override val direction: Direction,
  override val symbol: String,
  override val datetime: String,
  override val status: TransactionStatus,
  val gas: String,
  val gasPrice: String,
  val gasUsed: String,
  val functionName: String?
) : TransactionUiModel()
