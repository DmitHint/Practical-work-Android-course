package com.example.jetpack_compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetpack_compose.entities.Character
import com.example.jetpack_compose.paging_source.CharacterPagingSource
import kotlinx.coroutines.flow.Flow

class CharacterListViewModel: ViewModel() {

    val characters: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { CharacterPagingSource() },
    ).flow.cachedIn(viewModelScope)

}