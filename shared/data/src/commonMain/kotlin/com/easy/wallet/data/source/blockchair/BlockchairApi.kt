package com.easy.wallet.data.source.blockchair

import com.easy.wallet.data.source.blockchair.model.BlockChairNewDto

internal interface BlockchairApi {
    suspend fun getNewsList(limit: Int, offset: Int): List<BlockChairNewDto>
}
