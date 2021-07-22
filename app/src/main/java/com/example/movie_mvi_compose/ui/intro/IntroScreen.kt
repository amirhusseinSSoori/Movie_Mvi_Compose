package com.example.movie_mvi_compose.ui.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.movie_mvi_compose.ui.theme.black
import com.example.movie_mvi_compose.ui.theme.white


@Composable
fun Splash() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(black), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Mvi Movie",style = TextStyle(color = white))
    }

}