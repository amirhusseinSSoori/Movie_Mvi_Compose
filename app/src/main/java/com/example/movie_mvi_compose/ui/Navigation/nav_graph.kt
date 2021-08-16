package com.example.movie_mvi_compose.ui.Navigation

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*

import com.example.movie_mvi_compose.ui.details.DetailsContract
import com.example.movie_mvi_compose.ui.details.DetailsMovie
import com.example.movie_mvi_compose.ui.details.DetailsViewModel
import com.example.movie_mvi_compose.ui.intro.Splash
import com.example.movie_mvi_compose.ui.movie.MovieLazyList
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun InitialNavGraph(navController:NavHostController) {
    val scope = rememberCoroutineScope()
    AnimatedNavHost(navController = navController, startDestination = Screen.Intro.route) {
        composable(Screen.Intro.route,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    Screen.MovieRoute.route ->
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    Screen.MovieRoute.route ->
                        slideOutHorizontally(
                            targetOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    Screen.MovieRoute.route ->
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    else -> null
                }
            }) {
            Splash()
            scope.launch {
                delay(3000)
                navController.navigate(Screen.MovieRoute.route) {
                    popUpTo(Screen.Intro.route)
                    popUpTo(Screen.Intro.route) { inclusive = true }
                    launchSingleTop = true
                }
            }

        }
        addMovieList(navController)
        addMovieDetail()

    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addMovieDetail(
) {
    composable(
        route = Screen.DetailsRoute.route + "/{details}",
        arguments = Screen.DetailsRoute.arguments,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = { _, target ->
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ){
        val viewModel: DetailsViewModel = hiltViewModel()
        val id="${it.arguments?.get("details")}".toInt()
        DetailsMovie(id,viewModel,onEffect = {
            viewModel.setEvent(DetailsContract.Event.ShowDetails(id))
        })
    }
}
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addMovieList(
    navController: NavController) {
    composable(
        route = Screen.MovieRoute.route,
        exitTransition = {_, _ ->
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = { initial, _ ->
            slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ){
        val viewModel: MovieViewModel = hiltViewModel()
        MovieLazyList(navigateToDetailsScreen = {
            navController.navigate("${Screen.DetailsRoute.route}/$it")
        },viewModel)
    }
}

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>){
    object MovieRoute: Screen(
        route = "ScreenMovie",
        arguments = emptyList()
    )
    object Intro : Screen("ScreenIntro",   arguments = emptyList())
    object DetailsRoute: Screen(
        route = "ScreenDetails",
        arguments = listOf(navArgument("details") {
            type = NavType.IntType
        })
    )
}

