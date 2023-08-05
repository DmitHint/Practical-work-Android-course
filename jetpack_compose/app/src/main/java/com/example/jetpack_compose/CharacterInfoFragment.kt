package com.example.jetpack_compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetpack_compose.ui.theme.GrayEpisode
import com.example.jetpack_compose.ui.theme.InfoBackgroundColor

class CharacterInfoFragment : Fragment() {

    private val viewModel by viewModels<CharacterInfoViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                CharacterInfoViewModel() as T
        }
    }

    private val pageData by lazy { InfoPagingSource.pager(viewModel) }

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())

        val characterId = arguments?.getInt("character_id")!!


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    State.Start -> {
                        view.setContent {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = InfoBackgroundColor)
                            )
                        }

                        viewModel.performRequest(characterId)
                        viewModel.getEpisodes()
                    }

                    State.Loading -> {
                        view.setContent {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = InfoBackgroundColor)
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    State.Success -> {
                        view.setContent {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = InfoBackgroundColor)
//                                    .verticalScroll(rememberScrollState())
                            ) {
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = "Person details",
                                            style = MaterialTheme.typography.h6,
                                            color = Color.White
                                        )
                                    },
                                    backgroundColor = InfoBackgroundColor,
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            activity?.supportFragmentManager?.popBackStack()
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowBack,
                                                contentDescription = "Go Back",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                )

                                Photo(viewModel.character.image)

                                Text(
                                    modifier = Modifier.padding(
                                        start = 20.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                    text = viewModel.character.name,
                                    style = MaterialTheme.typography.h4,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                                Divider(
                                    modifier = Modifier.padding(bottom = 10.dp),
                                    color = Color.White, thickness = 1.dp
                                )

                                Text(
                                    modifier = Modifier.padding(start = 15.dp, bottom = 3.dp),
                                    text = "Live status:",
                                    style = MaterialTheme.typography.subtitle2,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                                Row(
                                    modifier = Modifier.padding(start = 15.dp),
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.circle),
                                        contentDescription = "status",
                                        modifier = Modifier
                                            .size(10.dp)
                                            .align(Alignment.CenterVertically),
                                        contentScale = ContentScale.Crop,
                                        colorFilter = when (viewModel.character.status) {
                                            "Alive" -> ColorFilter.tint(Color.Green)
                                            "unknown" -> ColorFilter.tint(Color.Gray)
                                            else -> ColorFilter.tint(Color.Red)
                                        }
                                    )

                                    Text(
                                        modifier = Modifier.padding(start = 10.dp),
                                        text = viewModel.character.status,
                                        style = MaterialTheme.typography.body1,
                                        color = Color.White
                                    )

                                }

                                Text(
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        top = 15.dp,
                                        bottom = 3.dp
                                    ),
                                    text = "Species and gender:",
                                    style = MaterialTheme.typography.subtitle2,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )

                                Text(
                                    modifier = Modifier.padding(start = 15.dp),
                                    text = "${viewModel.character.species}(${viewModel.character.gender})",
                                    style = MaterialTheme.typography.body2,
                                    color = Color.White
                                )

                                Text(
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        top = 15.dp,
                                        bottom = 5.dp
                                    ),
                                    text = "Last known location:",
                                    style = MaterialTheme.typography.subtitle2,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )

                                Text(
                                    modifier = Modifier.padding(start = 15.dp),
                                    text = viewModel.character.location.name,
                                    style = MaterialTheme.typography.body2,
                                    color = Color.White
                                )

                                Text(
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        top = 15.dp,
                                        bottom = 3.dp
                                    ),
                                    text = "First seen in:",
                                    style = MaterialTheme.typography.subtitle2,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )

                                Text(
                                    modifier = Modifier.padding(start = 15.dp),
                                    text = viewModel.character.episode[0],
                                    style = MaterialTheme.typography.body2,
                                    color = Color.White
                                )

                                Text(
                                    modifier = Modifier.padding(start = 15.dp, top = 20.dp, bottom = 10.dp),
                                    text = "Episodes:",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.h5,
                                    color = Color.White
                                )
                                EpisodesList()
                            }
                        }
                    }
                }
            }
        }
        return view
    }

    @ExperimentalGlideComposeApi
    @Composable
    fun Photo(url: Any?) {
        GlideImage(
            model = url,
            contentDescription = "My Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
    }


    @Composable
    fun EpisodeView(episode: Episode) {
        Card(
            shape = RoundedCornerShape(0.dp,8.dp,8.dp,0.dp),
            modifier = Modifier
                .padding(end = 10.dp, bottom = 5.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GrayEpisode)
            ) {

                val (episodeName) = createRefs()
                val (episodeCode) = createRefs()
                val (airDate) = createRefs()

                Text(
                    text = episode.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                    modifier = Modifier
                        .constrainAs(episodeName) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                        .layoutId(episodeName)
                        .padding(start = 10.dp)
                )

                Text(
                    text = episode.episode,
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.Gray,
                    modifier = Modifier
                        .constrainAs(episodeCode) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .layoutId(episodeCode)
                        .padding(end = 10.dp)
                )

                Text(
                    text = episode.air_date,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray,
                    modifier = Modifier
                        .constrainAs(airDate) {
                            top.linkTo(episodeName.bottom)
                            start.linkTo(parent.start)
                        }
                        .layoutId(airDate)
                        .padding(start = 10.dp)
                )

            }

        }
    }

    @Composable
    fun EpisodesList() {
        val items: LazyPagingItems<Episode> = pageData.flow.collectAsLazyPagingItems()

        LazyColumn {
            items(items.itemCount) {
                EpisodeView(episode = items[it]!!)
            }
            items.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                    }

                    loadState.append is LoadState.Error -> {
                    }

                }
            }
        }
    }
}