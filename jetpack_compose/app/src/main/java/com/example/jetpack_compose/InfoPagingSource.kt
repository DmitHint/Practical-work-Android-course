package com.example.jetpack_compose

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

class InfoPagingSource(
    private val viewModel: CharacterInfoViewModel
) : PagingSource<Int, Episode>() {

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> =
        kotlin.runCatching { viewModel.getEpisodes() }.fold(
            onSuccess = { list ->
                LoadResult.Page(
                    data = list,
                    prevKey = params.key?.let { it - 1 },
                    nextKey = (params.key ?: 0) + 1,
                )
            },
            onFailure = { LoadResult.Error(it) }
        )

    companion object {
        fun pager(viewModel: CharacterInfoViewModel) = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { InfoPagingSource(viewModel) }
        )
    }

}