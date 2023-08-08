package com.example.jetpack_compose.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetpack_compose.entities.Location
import com.example.jetpack_compose.paging_source.LocationPagingSource
import com.example.jetpack_compose.ui.theme.CardviewDark
import com.example.jetpack_compose.ui.theme.ListBackground
import com.example.jetpack_compose.ui.theme.Teal700
import com.example.jetpack_compose.viewmodels.LocationListViewModel

class LocationListFragment : Fragment() {

    private val viewModel by viewModels<LocationListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                LocationListViewModel() as T
        }
    }

    private val pageData by lazy { LocationPagingSource.pager(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            LocationListView()
        }
    }


    @Composable
    fun LocationView(location: Location) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp, end = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardviewDark)
                    .padding(start = 10.dp, end = 10.dp)

            ) {
                Text(
                    text = location.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )

                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = location.type,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier
                )

                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = location.dimension,
                    style = MaterialTheme.typography.h5,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )

            }

        }
    }

    @Composable
    fun LocationListView() {
        val items: LazyPagingItems<Location> = pageData.flow.collectAsLazyPagingItems()

        LazyColumn(Modifier.background(ListBackground)) {
            items(items.itemCount) { index ->
                val location = items[index]
                location?.let { LocationView(location = location) } ?: Text("Ooops")
            }

            items.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 10.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(color = Teal700)
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 10.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(color = Teal700)
                            }
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val e = items.loadState.refresh as LoadState.Error
                        item {
                            Column(modifier = Modifier.fillParentMaxSize()) {
                                e.error.localizedMessage?.let { Text(text = it) }
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val e = items.loadState.append as LoadState.Error
                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                e.error.localizedMessage?.let { Text(text = it) }
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }
                        }

                    }

                }
            }
        }
    }

}