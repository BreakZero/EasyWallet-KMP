package com.easy.wallet.shared.component

import com.easy.wallet.core.common.wrap
import com.easy.wallet.shared.data.repository.news.NewsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("NewsComponent")
class NewsComponent : KoinComponent {
    private val newsRepository: NewsRepository by inject()

    fun allNews(limit: Int, offset: Int) = newsRepository.loadNewsStream(limit, offset).wrap()
}
