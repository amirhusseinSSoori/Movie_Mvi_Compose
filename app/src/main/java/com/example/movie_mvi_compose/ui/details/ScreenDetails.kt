package com.example.movie_mvi_compose.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import com.example.movie_mvi_compose.ui.movie.NetworkImage
import com.google.accompanist.coil.rememberCoilPainter
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun DetailsMovie(ur:String){
    val context =  LocalContext.current
    val viewModel = hiltViewModel<DetailsViewModel>()
    val details = viewModel.state.collectAsState().value
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
            painter = rememberCoilPainter(request = "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/posters/${ur}.jpg"),
            contentDescription = null
        )
    }
   
}

