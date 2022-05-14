package com.example.movie_mvi_compose.ui.intro

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.movie_mvi_compose.R
import com.example.movie_mvi_compose.ui.Navigation.ScreenRoute
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
        navController.navigate(ScreenRoute.MovieRoute.route) {
            popUpTo(ScreenRoute.Intro.route) { inclusive = true }
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


