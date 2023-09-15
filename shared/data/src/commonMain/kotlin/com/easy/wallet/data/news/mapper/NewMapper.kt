package com.easy.wallet.data.news.mapper

import com.easy.wallet.data.source.blockchair.model.BlockChairNewDto
import com.easy.wallet.model.news.News

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
