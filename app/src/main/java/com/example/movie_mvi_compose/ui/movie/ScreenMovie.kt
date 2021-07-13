package com.example.movie_mvi_compose.ui.movie

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun MovieRowItem(uri: String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {}
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            painter = rememberCoilPainter(request = uri),
            contentDescription = null
        )

    }


}

@Composable
fun BtnRetry(content: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { content() }) {
            Text(text = "Retry")
        }

    }

}


@ExperimentalFoundationApi
@Composable
fun MovieLazyList(list: MovieResponse) {
    val scrollState = rememberLazyListState()
    LazyVerticalGrid(cells = GridCells.Fixed(4)) {
        items(list.size) { data ->
            MovieRowItem(list.get(data).poster)
        }
    }
}