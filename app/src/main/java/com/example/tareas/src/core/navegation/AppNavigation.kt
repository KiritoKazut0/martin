package com.example.tareas.src.core.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tareas.src.features.login.presentation.view.LoginScreen
import com.example.tareas.src.features.register.presentation.view.RegisterScreen
import com.example.tareas.src.features.tasks.presentation.view.CreateTaskScreen
import com.example.tareas.src.features.tasks.presentation.view.HomeScreen
import com.example.tareas.src.features.tasks.presentation.view.UpdateTaskScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {

        composable("Login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("Home") },
                onRegisterClick = { navController.navigate("Register") }
            )
        }

        composable("Register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("Home") },
                onLoginClick = { navController.navigate("Login") }
            )
        }

        composable("Home") {
            HomeScreen(
                onNavigateToCreate = { navController.navigate("Create"){
                    launchSingleTop = true
                } },
                onNavigateToUpdate = { taskId -> navController.navigate("Update/$taskId") }
            )
        }

        composable(
            route = "Update/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            UpdateTaskScreen(taskId = id)
        }

        composable("Create") {
            CreateTaskScreen()
        }
    }
}
