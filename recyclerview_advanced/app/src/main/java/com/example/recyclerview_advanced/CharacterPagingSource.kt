package com.example.recyclerview_advanced

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class CharacterPagingSource : PagingSource<Int, Character>() {
    private val repository = CharactersRepository()
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getCharacters(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }

}
