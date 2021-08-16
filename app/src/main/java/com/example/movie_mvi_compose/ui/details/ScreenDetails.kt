package com.example.movie_mvi_compose.ui.details


import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movie_mvi_compose.R
import com.example.movie_mvi_compose.ui.base.Loader
import com.example.movie_mvi_compose.ui.base.ProgressBarState
import com.example.movie_mvi_compose.ui.base.utilFont
import com.example.movie_mvi_compose.ui.movie.MovieContract
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import com.example.movie_mvi_compose.ui.theme.black
import com.example.movie_mvi_compose.ui.theme.white
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@Composable
fun DetailsMovie(id: String) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val details by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = MovieContract.Effect.Empty)
    var visible by remember { mutableStateOf(true) }


    LaunchedEffect(true) {
        viewModel.setEvent(DetailsContract.Event.ShowDetails(id.toInt()))
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {

        details.let { details ->
            when (details.state) {
                is DetailsContract.DetailsState.Loading ->{
                    Loading(progressBarState = details.state.data)
                }
                is DetailsContract.DetailsState.Success -> {
                    val info = details.state.details
                    visible = false
                    ScreenDetails(
                        info.poster!!,
                        info.title!!,
                        info.cast!!,
                        info.director!!,
                        info.year!!,
                        info.summary!!
                    )

                }

                else ->Unit
            }


        }

        effect.let { effect ->
            when (effect) {
                is DetailsContract.Effect.ShowError -> {
                    Log.e("ErrorConnection : ", effect.message, )
                    ErrorConnection(updateUi = {
                        viewModel.setEvent(DetailsContract.Event.ShowDetails(id.toInt())) },visible)
                }
            }
        }


    }

}

@ExperimentalAnimationApi
@Composable
fun ErrorConnection(updateUi : () ->Unit ,visible:Boolean) {
    AnimatedVisibility(visible = visible) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(black), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Loader(R.raw.poor)
            Text(text = stringResource(id =R.string.error_connection ),modifier = Modifier.clickable {
                updateUi()


            },color = white)
        }
    }



}
@Composable
fun Loading(progressBarState: ProgressBarState) {
    if (progressBarState is ProgressBarState.Loading) {
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
fun ScreenDetails(
    url: String,
    title: String,
    cast: String,
    director: String,
    year: String,
    summery: String
) {
    BackGroundImage(url)
    Hover()
    MovieDescription(title, cast, director, year, summery)
}


@Composable
fun MovieDescription(
    dsTitle: String,
    dsCast: String,
    dsDirector: String,
    dsYear: String,
    dsSummery: String
) {
    val scroll = rememberScrollState(0)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
    ) {
        val (title, topicCast, cast, director, topicYearDirector, topicSummery, summery) = createRefs()


        //  Title
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(
                    start =
                    5.dp, end = 5.dp, top = 5.dp
                )
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 15.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            elevation = 10.dp,
            backgroundColor = Color(0x73F8F6F6),
        ) {
            DetailsBox(des = dsTitle)

        }

        // Cast
        Text(
            text = stringResource(id =R.string.cast), color = Color(0xA3F3F1F1), modifier = Modifier
                .constrainAs(topicCast) {
                    top.linkTo(title.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(), fontFamily = utilFont,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,

        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(
                    start =
                    5.dp, end = 5.dp, top = 5.dp
                )
                .constrainAs(cast) {
                    top.linkTo(topicCast.bottom, margin = 5.dp)
                    end.linkTo(parent.end, margin = 5.dp)
                    start.linkTo(parent.start, margin = 5.dp)
                },
            elevation = 10.dp,
            backgroundColor = Color(0x73F8F6F6),
        ) {
            DetailsBox(des = dsCast)

        }
        //Director


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topicYearDirector) {
                    top.linkTo(cast.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.director), color = Color(0x73F8F6F6), fontFamily = utilFont,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = stringResource(id = R.string.Year), color = Color(0x73F8F6F6), fontFamily = utilFont,
                fontWeight = FontWeight.Normal
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(director) {
                    top.linkTo(topicYearDirector.bottom)
                    end.linkTo(parent.end, margin = 5.dp)
                    start.linkTo(parent.start, margin = 5.dp)
                },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .padding(
                        start =
                        5.dp, top = 5.dp
                    ),
                elevation = 10.dp,
                backgroundColor = Color(0x73F8F6F6),
            ) {
                DetailsBox(des = dsDirector)

            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .padding(
                        start =
                        5.dp, top = 5.dp
                    ),
                elevation = 10.dp,
                backgroundColor = Color(0x73F8F6F6),
            ) {
                DetailsBox(des = dsYear)
            }
        }
        // Summery
        Text(
            text = stringResource(id = R.string.summery),
            color = Color(0xA3F3F1F1),
            modifier = Modifier
                .constrainAs(topicSummery) {
                    top.linkTo(director.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(),
            fontFamily = utilFont,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start =
                    5.dp, end = 5.dp, top = 5.dp
                )
                .constrainAs(summery) {
                    top.linkTo(topicSummery.bottom, margin = 5.dp)
                    end.linkTo(parent.end, margin = 5.dp)
                    start.linkTo(parent.start, margin = 5.dp)
                },
            elevation = 10.dp,
            backgroundColor = Color(0x73F8F6F6),
        ) {
            DetailsBox(dsSummery)
        }

    }


}

@Composable
fun DetailsBox(des: String) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = des, color = black, fontFamily = utilFont,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
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