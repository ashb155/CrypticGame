package com.example.crypticgame.navigation

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crypticgame.ui.screens.MainMenuScreen
import com.example.crypticgame.ui.screens.AboutScreen



sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object About : Screen("about")
    object Chapter : Screen("chapter")
    object Puzzle : Screen("puzzle/{puzzleId}") {
        fun createRoute(puzzleId: Int) = "puzzle/$puzzleId"
    }
}


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainMenu.route
    ) {
        composable(Screen.MainMenu.route) {
            MainMenuScreen(
                onStart = { navController.navigate(Screen.Chapter.route) },
                onAbout = { navController.navigate(Screen.About.route) }
            )
        }
        composable(
            route = Screen.About.route,
            enterTransition = {
                fadeIn(tween(500, easing = EaseInOut)) +
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500, easing = EaseInOut))
            },
            exitTransition = {
                fadeOut(tween(400, easing = EaseInOut)) +
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(400, easing = EaseInOut))
            },
            popEnterTransition = {
                fadeIn(tween(500, easing = EaseInOut)) +
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500, easing = EaseInOut))
            },
            popExitTransition = {
                fadeOut(tween(400, easing = EaseInOut)) +
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(400, easing = EaseInOut))
            }
        ) {
            AboutScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = Screen.Chapter.route,
            enterTransition = {
                fadeIn(tween(500, easing = EaseInOut)) +
                        slideInVertically(initialOffsetY = { it }, animationSpec = tween(500, easing = EaseInOut))
            },
            exitTransition = {
                fadeOut(tween(400, easing = EaseInOut)) +
                        slideOutVertically(targetOffsetY = { it }, animationSpec = tween(400, easing = EaseInOut))
            },
            popEnterTransition = {
                fadeIn(tween(500, easing = EaseInOut)) +
                        slideInVertically(initialOffsetY = { it }, animationSpec = tween(500, easing = EaseInOut))
            },
            popExitTransition = {
                fadeOut(tween(400, easing = EaseInOut)) +
                        slideOutVertically(targetOffsetY = { it }, animationSpec = tween(400, easing = EaseInOut))
            }
        ) {

        }

        composable(Screen.Puzzle.route) {

        }
    }
}


