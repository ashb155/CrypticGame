package com.example.crypticgame.navigation

import PuzzleViewModelFactory
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crypticgame.data.db.AppDatabase
import com.example.crypticgame.ui.components.CrypticButton
import com.example.crypticgame.ui.screens.AboutScreen
import com.example.crypticgame.ui.screens.MainMenuScreen
import com.example.crypticgame.ui.screens.PuzzleScreen
import com.example.crypticgame.ui.theme.BackgroundDark
import com.example.crypticgame.viewmodel.PuzzleViewModel

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object About : Screen("about")
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
                onStart = { navController.navigate(Screen.Puzzle.createRoute(1)) },
                onAbout = { navController.navigate(Screen.About.route) }
            )
        }

        composable(
            route = Screen.About.route,
            enterTransition = {
                fadeIn(tween(800, easing = EaseInOut)) +
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500, easing = EaseInOut))
            },
            exitTransition = {
                fadeOut(tween(600, easing = EaseInOut)) +
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(400, easing = EaseInOut))
            },
            popEnterTransition = {
                fadeIn(tween(800, easing = EaseInOut)) +
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500, easing = EaseInOut))
            },
            popExitTransition = {
                fadeOut(tween(600, easing = EaseInOut)) +
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(400, easing = EaseInOut))
            }
        ) {
            AboutScreen(onBack = { navController.popBackStack() })
        }


        composable(Screen.Puzzle.route,
            enterTransition = {
                fadeIn(tween(1500, delayMillis = 500, easing = EaseInOut)) +
                    slideInVertically(initialOffsetY = { it }, animationSpec = tween(800, easing = EaseInOut))
        },
            exitTransition = {
                fadeOut(tween(1000, delayMillis = 300, easing = EaseInOut)) +
                        slideOutVertically(targetOffsetY = { it }, animationSpec = tween(600, easing = EaseInOut))
            },
            popEnterTransition = {
                fadeIn(tween(1500, delayMillis = 500, easing = EaseInOut)) +
                        slideInVertically(initialOffsetY = { it }, animationSpec = tween(800, easing = EaseInOut))
            },
            popExitTransition = {
                fadeOut(tween(1000, delayMillis = 300, easing = EaseInOut)) +
                        slideOutVertically(targetOffsetY = { it }, animationSpec = tween(600, easing = EaseInOut))
            }) { backStackEntry ->
            val context = LocalContext.current
            val puzzleIdString = backStackEntry.arguments?.getString("puzzleId")
            val puzzleId = puzzleIdString?.toIntOrNull() ?: 1

            val db = AppDatabase.getDatabase(context)
            val dao = db.progressDao()


            val viewModel: PuzzleViewModel = viewModel(
                factory = PuzzleViewModelFactory(puzzleId, dao)
            )

            PuzzleScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}