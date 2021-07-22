package com.example.movie_mvi_compose.ui.details


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movie_mvi_compose.ui.movie.MovieContract
import com.example.movie_mvi_compose.ui.theme.black
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun DetailsMovie(id: String) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val details by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = MovieContract.Effect.Empty)

    viewModel.setEvent(DetailsContract.Event.ShowDetails(id.toInt()))
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {

        details.let { details ->
            when (details.state) {
                is DetailsContract.DetailsState.Success -> {
                    val info = details.state.details
                    ScreenDetails(
                        info.poster!!,
                        info.title!!,
                        info.cast!!,
                        info.director!!,
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

}

@Composable
fun ScreenDetails(url: String, title: String, cast: String,director:String) {
    BackGroundImage(url)
    Hover()
    DescriptionMovie(dsTitle = title, cast,director )
}


@Composable
fun DescriptionMovie(dsTitle: String, dsSummary: String,dsDirector:String) {
    val scroll = rememberScrollState(0)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
    ) {
        val (title, topicCast,cast,topicDirector,director) = createRefs()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start =
                5.dp, end = 5.dp, top = 5.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 15.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            elevation = 10.dp,
            backgroundColor = Color(0x73F8F6F6),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    dsTitle, color = black, modifier = Modifier,
                )
            }

        }


        Text(text = "cast", color = Color(0xA3F3F1F1),modifier = Modifier.constrainAs(topicCast){
            top.linkTo(title.bottom,margin = 5.dp)
            start.linkTo(parent.start,margin = 15.dp)
        })

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start =
                5.dp, end = 5.dp, top = 5.dp)
                .constrainAs(cast) {
                    top.linkTo(topicCast.bottom, margin = 5.dp)
                    end.linkTo(parent.end, margin = 5.dp)
                    start.linkTo(parent.start, margin = 5.dp)
                },
            elevation = 10.dp,
            backgroundColor = Color(0x73F8F6F6),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = dsSummary, color = black)

            }

        }

        Text(text = "director", color = Color(0xA3F3F1F1),modifier = Modifier.constrainAs(topicDirector){
            top.linkTo(cast.bottom,margin = 5.dp)
            start.linkTo(parent.start,margin = 15.dp)
        })

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start =
                5.dp, top = 5.dp)
                .constrainAs(director) {
                    top.linkTo(topicDirector.bottom, margin = 5.dp)
                    end.linkTo(parent.end, margin = 5.dp)
                    start.linkTo(parent.start, margin = 5.dp)
                },
            elevation = 10.dp,
            backgroundColor = Color(0x73F8F6F6),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = dsDirector, color = black)

            }

        }





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