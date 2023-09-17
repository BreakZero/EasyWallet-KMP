package com.easy.wallet.data.source.blockchair

import com.easy.wallet.data.source.blockchair.model.BlockChairNewDto
import com.easy.wallet.data.source.blockchair.model.DashboardResponse

internal interface BlockchairApi {
    suspend fun getNewsList(limit: Int, offset: Int): List<BlockChairNewDto>

    suspend fun getDashboardByAccount(chain: String, account: String): DashboardResponse
}
