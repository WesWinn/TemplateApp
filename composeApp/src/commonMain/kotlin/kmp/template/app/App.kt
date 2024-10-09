package kmp.template.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.DetailsScreen
import ui.TodoListScreen

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            TodoListScreen(
                onTodoClick = { todoId -> navController.navigate("details/$todoId") }
            )
        }
        composable(
            route = "details/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: 0
            DetailsScreen(todoId = todoId, onBack = { navController.popBackStack() })
        }
    }
}