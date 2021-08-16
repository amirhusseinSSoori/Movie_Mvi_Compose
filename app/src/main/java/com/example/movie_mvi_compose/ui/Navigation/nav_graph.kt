package com.example.movie_mvi_compose.ui.Navigation

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.movie_mvi_compose.ui.Navigation.NavScreen.Details.argument0
import com.example.movie_mvi_compose.ui.Navigation.NavScreen.Details.routeWithArgument
import com.example.movie_mvi_compose.ui.details.DetailsContract
import com.example.movie_mvi_compose.ui.details.DetailsMovie
import com.example.movie_mvi_compose.ui.details.DetailsViewModel
import com.example.movie_mvi_compose.ui.intro.Splash
import com.example.movie_mvi_compose.ui.movie.MovieContract
import com.example.movie_mvi_compose.ui.movie.MovieLazyList
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun InitialNavGraph() {

    val navController = rememberAnimatedNavController()
    val scope = rememberCoroutineScope()

    AnimatedNavHost(navController = navController, startDestination = NavScreen.Intro.route) {

        composable(NavScreen.Intro.route,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    NavScreen.Movie.route ->
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    NavScreen.Movie.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    NavScreen.Movie.route ->
                        slideInHorizontally(
                            initialOffsetX = { -300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    else -> null
                }
            }) {
            Splash()
            scope.launch {
                delay(3000)

                navController.navigate(NavScreen.Movie.route) {
                    popUpTo(NavScreen.Intro.route)
                    popUpTo(NavScreen.Intro.route) { inclusive = true }
                    launchSingleTop = true
                }
            }

        }

        composable(NavScreen.Movie.route, enterTransition = { initial, _ ->
            when (initial.destination.route) {
                NavScreen.Details.route ->
                    slideInHorizontally(
                        initialOffsetX = { 100 },
                        animationSpec = tween(100)
                    ) + fadeIn(animationSpec = tween(100))
                else -> null
            }
        },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    NavScreen.Details.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween(100)
                        ) + fadeOut(animationSpec = tween(100))
                    else -> null
                }
            }


        ) { backStackEntry ->
            val viewModel = hiltViewModel<MovieViewModel>(backStackEntry)

            MovieLazyList(navigateToDetailsScreen = {
                navController.navigate("${NavScreen.Details.route}/$it")
            }, viewModel = viewModel)
        }




        composable(
            routeWithArgument,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    NavScreen.Movie.route ->
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    NavScreen.Movie.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            },
            popExitTransition = { _, target ->
                when (target.destination.route) {
                    NavScreen.Movie.route ->
                        slideOutHorizontally(
                            targetOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            },
            arguments = listOf(navArgument(argument0) { type = NavType.StringType })
        ) {
            DetailsMovie("${it.arguments?.get(argument0)}")

        }
    }
}


sealed class NavScreen(val route: String) {

    object Movie : NavScreen("ScreenMovie")
    object Intro : NavScreen("ScreenIntro")
    object Details : NavScreen("ScreenDetails") {

        const val routeWithArgument: String = "ScreenDetails/{details}"

        const val argument0: String = "details"
    }
}
