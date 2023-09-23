package com.easy.wallet.network.source.blockchair

import com.easy.wallet.network.source.blockchair.dto.BlockChairNewDto
import com.easy.wallet.network.source.blockchair.dto.DashboardResponse

interface BlockchairApi {
    suspend fun getNewsList(limit: Int, offset: Int): List<BlockChairNewDto>

    suspend fun getDashboardByAccount(chain: String, account: String): DashboardResponse
}
