package com.example.movie_mvi_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.example.movie_mvi_compose.ui.Navigation.InitialNavGraph
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import com.example.movie_mvi_compose.ui.theme.Movie_Mvi_ComposeTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @VisibleForTesting
    val viewModel: MovieViewModel by viewModels()
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Movie_Mvi_ComposeTheme {
                val navController: NavHostController = rememberAnimatedNavController()
                Surface(color = MaterialTheme.colors.background) {

                        InitialNavGraph(navController)
                    }

            }
        }
    }
}



