package com.example.movie_mvi_compose.ui.details


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movie_mvi_compose.ui.movie.BtnRetry
import com.example.movie_mvi_compose.ui.movie.MovieContract
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlin.coroutines.CoroutineContext


@Composable
fun DetailsMovie(id: String) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val details by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = MovieContract.Effect.Empty)

    viewModel.setEvent(DetailsContract.Event.ShowDetails(id.toInt()))

    details.let { details ->
        when (details.state) {
            is DetailsContract.DetailsState.Success -> {
                ScreenDetails(
                    details.state.details.poster!!,
                    details.state.details.title!!,
                    details.state.details.summary!!
                )
            }
        }

    }

    effect.let { effect ->
        when (effect) {
            is DetailsContract.Effect.ShowError -> {
                Toast.makeText(LocalContext.current, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}

@Composable
fun ScreenDetails(url: String, title: String, summary: String) {
    BackGroundImage(url)
    Hover()
    DescriptionMovie(dsTitle = title, summary)
}


@Composable
fun DescriptionMovie(dsTitle: String, dsSummary: String) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (title, summery) = createRefs()

        Text(
            dsTitle, color = Color(0xA3F3F1F1),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 15.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            },
        )
        Text(
            text = dsSummary, color = Color(0xA3F3F1F1),
            modifier = Modifier.constrainAs(summery) {
                top.linkTo(title.bottom, margin = 7.dp)
                end.linkTo(parent.end, margin = 7.dp)
                start.linkTo(parent.start, margin = 10.dp)
            },
        )

    }
}

@Composable
fun Hover() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xBF020202))
    ) {}
}

@Composable
fun BackGroundImage(uri: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        CoilImage(
            imageModel = uri,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
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


}

