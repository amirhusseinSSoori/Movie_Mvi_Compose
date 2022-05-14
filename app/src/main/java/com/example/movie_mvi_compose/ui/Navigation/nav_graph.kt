package com.example.movie_mvi_compose.ui.Navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.movie_mvi_compose.ui.details.DetailsMovie
import com.example.movie_mvi_compose.ui.details.DetailsViewModel
import com.example.movie_mvi_compose.ui.intro.Splash
import com.example.movie_mvi_compose.ui.movie.MovieLazyList
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun InitialNavGraph(navController: NavHostController) {
    AnimatedNavHost(navController = navController, startDestination = ScreenRoute.MovieRoute.route) {
//        addIntro(navController)
        addMovieList(navController)
        addMovieDetail()

    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addIntro(navController: NavController) {
    composable(
        ScreenRoute.Intro.route,
        enterTransition = { when (initialState.destination.route) {
            ScreenRoute.MovieRoute.route ->
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            else -> null
        }},
        exitTransition = {
            when (initialState.destination.route) {
                ScreenRoute.MovieRoute.route ->
                    slideOutHorizontally(
                        targetOffsetX = { 300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                ScreenRoute.MovieRoute.route ->
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                else -> null
            }
        }) {
        Splash(navController)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addMovieDetail(
) {
    composable(
        route = ScreenRoute.DetailsRoute.route + "/{details}",
        arguments = ScreenRoute.DetailsRoute.arguments,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { -300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        val viewModel: DetailsViewModel = hiltViewModel()
        val id = "${it.arguments?.get("details")}".toInt()
        DetailsMovie(id, viewModel)
    }
}


@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
fun NavGraphBuilder.addMovieList(
    navController: NavController
) {
    composable(
        route = ScreenRoute.MovieRoute.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
        val viewModel: MovieViewModel = hiltViewModel()
        MovieLazyList(navigateToDetailsScreen = {
            navController.navigate("${ScreenRoute.DetailsRoute.route}/$it") {}
        }, viewModel)
    }
}


