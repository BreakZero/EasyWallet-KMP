package com.easy.wallet.design.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> DefaultPagingStateColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    paging: LazyPagingItems<T>,
    header: LazyListScope.() -> Unit = {},
    footer: LazyListScope.() -> Unit = {},
    itemKey: ((index: Int) -> Any)? = {
        paging[it].hashCode()
    },
    itemView: @Composable LazyItemScope.(T) -> Unit
) {
    LazyColumn(
        modifier,
        state = state,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
    ) {
        header()
        items(
            key = itemKey,
            count = paging.itemCount
        ) {
            itemView(paging[it]!!)
        }
        paging.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            LoadingWheel(contentDesc = "")
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Button(onClick = { refresh() }) {
                                Text(text = "tap to refresh...")
                            }
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            LoadingWheel(contentDesc = "")
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Button(onClick = ::retry) {
                                Text(text = "load more failed...")
                            }
                        }
                    }
                }
            }
        }
        footer()
    }
}
