package com.example.recyclerview_advanced

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class CharactersViewModel: ViewModel() {

    val characters: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {CharacterPagingSource()},
    ).flow.cachedIn(viewModelScope)

}