package com.example.movie_mvi_compose.ui.Navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class ScreenRoute(val route: String, val arguments: List<NamedNavArgument>) {
    object MovieRoute : ScreenRoute(
        route = "ScreenMovie",
        arguments = emptyList()
    )

    object Intro : ScreenRoute("ScreenIntro", arguments = emptyList())
    object DetailsRoute : ScreenRoute(
        route = "ScreenDetails",
        arguments = listOf(navArgument("details") {
            type = NavType.IntType
        })
    )
}
