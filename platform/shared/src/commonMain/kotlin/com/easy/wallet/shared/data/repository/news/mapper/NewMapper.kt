package com.easy.wallet.shared.data.repository.news.mapper

import com.easy.wallet.model.news.News
import com.easy.wallet.network.source.blockchair.model.BlockChairNewDto

internal fun BlockChairNewDto.externalModel(): News {
    return News(
        title = title,
        source = source,
        language = language,
        link = link,
        time = time,
        hash = hash,
        description = description,
        tags = tags,
    )
}
