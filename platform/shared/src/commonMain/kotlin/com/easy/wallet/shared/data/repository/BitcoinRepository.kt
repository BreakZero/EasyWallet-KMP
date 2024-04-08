package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.fees.FeeLevel
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BitcoinRepository : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
//        println("===== Bitcoin Address $account")
        return flow {
            emit(emptyList())
        }
    }

    override fun loadBalance(account: String): Flow<String> {
        return flow {
            emit("Bitcoin Balances")
        }
    }

    override suspend fun loadTransactions(
        token: TokenBasicResult,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        TODO("Not yet implemented")
    }

    override suspend fun prepFees(
        account: String,
        toAddress: String,
        contractAddress: String?,
        amount: String
    ): List<FeeModel>  {
        TODO("Not yet implemented")
    }

    override suspend fun signTransaction(
        account: String,
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String?,
        amount: String,
        fee: FeeModel
    ): String {
        TODO("Not yet implemented")
    }

}
