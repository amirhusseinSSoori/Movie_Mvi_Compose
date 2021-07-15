package com.example.movie_mvi_compose.ui.movie

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.google.accompanist.coil.rememberCoilPainter
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun MovieRowItem(uri: String,context: Context,navigation: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                Toast.makeText(context,uri,Toast.LENGTH_SHORT).show()
                navigation()
            }
    ) {
        NetworkImage(url = uri,
            modifier =Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)))
    }
}


@Composable
fun BtnRetry(UiUpdatePoorConnection:MovieViewModel){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { UiUpdatePoorConnection.showAllMovie() }) {
            Text(text = "Retry")
        }

    }
}



@ExperimentalFoundationApi
@Composable
fun MovieLazyList(navigation:() -> Unit) {
    val context =  LocalContext.current
    val viewModel = hiltViewModel<MovieViewModel>()
    val list = viewModel.state.collectAsState().value
    val error by remember { viewModel.handelError }
    LazyVerticalGrid(cells = GridCells.Fixed(4)) {
        items(list.size) { data ->
            MovieRowItem(list[data].poster,context,navigation)
        }
    }
    viewModel.showAllMovie()
    if(error == "Failure"){
        BtnRetry(viewModel)
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
