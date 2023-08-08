package com.example.jetpack_compose.paging_source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetpack_compose.entities.Location
import com.example.jetpack_compose.viewmodels.LocationListViewModel

class LocationPagingSource(
    private val viewModel: LocationListViewModel

) : PagingSource<Int, Location>() {

    override fun getRefreshKey(state: PagingState<Int, Location>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> =
            kotlin.runCatching { viewModel.load(params.key ?: 0) }.fold(
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
        fun pager(viewModel: LocationListViewModel) = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { LocationPagingSource(viewModel) }
        )
    }

}