package com.example.m17_recyclerview.photomarslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.m17_recyclerview.models.PhotoMars
import kotlinx.coroutines.flow.Flow

class PhotoMarsListViewModel : ViewModel() {
    val photosMars: Flow<PagingData<PhotoMars>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {PhotoMarsPagingSource()},
    ).flow.cachedIn(viewModelScope)

}