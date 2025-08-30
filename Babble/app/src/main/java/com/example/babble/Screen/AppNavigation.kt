package com.example.babble.Screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.babble.item.EventViewModel
import com.example.babble.item.EventViewModelFactory
import com.example.babble.item.EventsApplication
import kotlinx.coroutines.MainScope

@Composable
fun AppNavigation(application: EventsApplication){
    val navController = rememberNavController()
    val viewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(application.repository)
    )

    NavHost(navController = navController, startDestination = "main_screen"){
        composable("main_screen"){
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable("post_screen"){
            PostScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = "detail_screen/{eventId}",
            arguments = listOf(navArgument("eventId"){ type = NavType.IntType})
        ){backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId")
            requireNotNull(eventId){"Event ID is required"}
            DetailScreen(navController = navController, viewModel = viewModel, eventId = eventId)
        }
        composable(
            route = "edit_screen/{eventId}",
            arguments = listOf(navArgument("eventId"){type = NavType.IntType})
        ){backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId")
            requireNotNull(eventId) {"Event ID is required"}
            EditScreen(navController = navController, viewModel = viewModel, eventId = eventId)
        }
    }

}