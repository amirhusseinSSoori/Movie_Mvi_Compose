package com.example.movie_mvi_compose.ui.details


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlin.coroutines.CoroutineContext


@Composable
fun DetailsMovie(id: String) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val details by viewModel.state.collectAsState()
    viewModel.showDetails(id.toInt())









    details.let { details->
        when(details){
            is DetailsViewModel.NewStatus.CheckMessage ->{
                BackGroundImage(uri = details.message.poster!!)
            }
        }

    }






    Hover()
//    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
//
//        val (title,summery) = createRefs()
//
//        Text(
//            "${details.title}", color = Color(0xA3F3F1F1),
//            modifier = Modifier.constrainAs(title) {
//                top.linkTo(parent.top, margin = 15.dp)
//                end.linkTo(parent.end)
//                start.linkTo(parent.start)
//            },
//        )
//        Text(text =  "${details.summary}", color = Color(0xA3F3F1F1),
//            modifier = Modifier.constrainAs(summery) {
//                top.linkTo(title.bottom, margin = 7.dp)
//                end.linkTo(parent.end,margin = 7.dp)
//                start.linkTo(parent.start,margin = 10.dp)
//            },)
//
//    }


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

