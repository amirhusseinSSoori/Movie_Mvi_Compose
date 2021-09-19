package com.example.movie_mvi_compose.ui.intro

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movie_mvi_compose.R
import com.example.movie_mvi_compose.ui.Navigation.Screen
import com.example.movie_mvi_compose.ui.base.Loader
import com.example.movie_mvi_compose.ui.base.utilFont
import com.example.movie_mvi_compose.ui.theme.black
import com.example.movie_mvi_compose.ui.theme.white
import kotlinx.coroutines.delay


@Composable
fun Splash(navController:NavController) {
    val scale = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate(Screen.MovieRoute.route) {
            popUpTo(Screen.Intro.route) { inclusive = true }
            launchSingleTop = true
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(black), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Loader(R.raw.cinema)
        Text(text = stringResource(id =R.string.intro ),color = white,fontFamily = utilFont,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center)
    }

}


