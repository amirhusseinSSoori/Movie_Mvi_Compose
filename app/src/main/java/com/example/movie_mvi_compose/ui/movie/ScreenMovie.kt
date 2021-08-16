package com.example.movie_mvi_compose.ui.movie

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movie_mvi_compose.R
import com.example.movie_mvi_compose.ui.theme.black
import com.example.movie_mvi_compose.ui.theme.white
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


@ExperimentalAnimationApi
@Composable
fun BtnRetry(UiUpdatePoorConnection: MovieViewModel,error:Boolean) {
    AnimatedVisibility(visible = error) {
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


}


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MovieLazyList(navigateToDetailsScreen: (id: String) -> Unit, viewModel: MovieViewModel) {
    val data by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = MovieContract.Effect.Empty)
    var visible by remember { mutableStateOf(true) }
    var error by  remember { mutableStateOf(true) }
    ConstraintLayout(
        modifier = Modifier
            .background(black)
            .fillMaxSize()
    ) {
        Loading(visible = visible)
        LazyVerticalGrid(cells = GridCells.Fixed(4)) {
            data.let {
                when (it.state) {
                    is MovieContract.MovieState.Movie -> {
                       val items=it.state.list
                        visible = false
                        items(items.size) { data ->
                            val (id, poster) = items!![data]
                            MovieRowItem(
                                poster!!,
                                navigateToDetailsScreen, id!!
                            )

                        }
                    }
                    else -> Unit
                }

            }

        }
        effect.let { effect ->
            when (effect) {
                is MovieContract.Effect.ShowError -> {
                    if(effect.list.isEmpty()){
                        BtnRetry(viewModel,error)
                    }
                }
            }

        }

    }
}

@Composable
fun Loading(visible: Boolean) {
    if (visible) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = white)
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
                text = stringResource(id = R.string.problem),
                style = MaterialTheme.typography.body2
            )
        }
    )
}
