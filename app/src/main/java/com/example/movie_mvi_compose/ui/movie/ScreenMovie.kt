package com.example.movie_mvi_compose.ui.movie

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.movie_mvi_compose.ui.theme.black
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun MovieRowItem(uri: String, navigateToDetailsScreen: (id: String) -> Unit, id: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                navigateToDetailsScreen(id)
            }
    ) {
        NetworkImage(
            url = uri,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
        )
    }
}


@Composable
fun BtnRetry(UiUpdatePoorConnection: MovieViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { UiUpdatePoorConnection.setEvent(MovieContract.Event.ShowMovie) }) {
            Text(text = "Retry")
        }

    }
}


@ExperimentalFoundationApi
@Composable
fun MovieLazyList(navigateToDetailsScreen: (id: String) -> Unit, viewModel: MovieViewModel) {
    val data by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = MovieContract.Effect.Empty)


    Column(modifier = Modifier
        .background(black)
        .fillMaxSize()) {
        LazyVerticalGrid(cells = GridCells.Fixed(4)) {
            data.let {
                when (it.state) {
                    is MovieContract.MovieState.Movie -> {
                        items(it.state.list.data!!.size) { data ->
                            val (id, poster) = it.state.list.data!![data]
                            MovieRowItem(
                                poster!!,
                                navigateToDetailsScreen, id!!
                            )
                        }
                    }
                    is MovieContract.MovieState.Idle -> {
                    }
                    else -> Unit
                }

            }

        }
        effect.let { effect ->
            when (effect) {
                is MovieContract.Effect.ShowError -> {
                    BtnRetry(viewModel)

                }
            }

        }
    }


}

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier,
    circularRevealedEnabled: Boolean = false,
    contentScale: ContentScale = ContentScale.Crop
) {
    CoilImage(
        imageModel = url,
        modifier = modifier,
        contentScale = contentScale,
        circularRevealedEnabled = circularRevealedEnabled,
        circularRevealedDuration = 450,
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = Color(0xA3C2C2C2),
            dropOff = 0.65f
        ),
        failure = {
            Text(
                text = "image request failed.",
                style = MaterialTheme.typography.body2
            )
        }
    )
}
