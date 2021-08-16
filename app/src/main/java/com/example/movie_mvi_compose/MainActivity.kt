package com.example.movie_mvi_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.movie_mvi_compose.ui.Navigation.InitialNavGraph
import com.example.movie_mvi_compose.ui.theme.Movie_Mvi_ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Movie_Mvi_ComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    InitialNavGraph()
                }
            }
        }
    }
}



