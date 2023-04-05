package com.example.m17_recyclerview.photomarslist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.m17_recyclerview.models.PhotoMars

class PhotoMarsPagingSource : PagingSource<Int, PhotoMars>() {
    private val repository = PhotoMarsListRepository()
    override fun getRefreshKey(state: PagingState<Int, PhotoMars>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoMars> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getMarsPhotos(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }

}