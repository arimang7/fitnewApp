package com.example.fitnessapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddEdit : Screen("add_edit?workoutId={workoutId}") {
        fun createRoute(workoutId: Int?) = "add_edit?workoutId=${workoutId ?: -1}"
    }
    object History : Screen("history")
    object Stats : Screen("stats")
    object Settings : Screen("settings")
    object BloodPressure : Screen("blood_pressure")
}

@Composable
fun FitnessAppNavigation(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        Pair(Screen.Home, Pair(Icons.Filled.Home, "Home")),
        Pair(Screen.History, Pair(Icons.Filled.DateRange, "Calendar")),
        Pair(Screen.Stats, Pair(Icons.Filled.ShowChart, "Stats")),
        Pair(Screen.Settings, Pair(Icons.Filled.Settings, "Settings"))
    )

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.History.route,
        Screen.Stats.route,
        Screen.Settings.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFF1E1E24),
                    contentColor = Color.White
                ) {
                    bottomNavItems.forEach { (screen, info) ->
                        val (icon, label) = info
                        val isSelected = currentRoute == screen.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (!isSelected) {
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF4A90E2),
                                selectedTextColor = Color(0xFF4A90E2),
                                unselectedIconColor = Color(0xFFA0A0A0),
                                unselectedTextColor = Color(0xFFA0A0A0),
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        onNavigateToAddEdit = { workoutId ->
                            navController.navigate(Screen.AddEdit.createRoute(workoutId))
                        },
                        onNavigateToBloodPressure = { navController.navigate(Screen.BloodPressure.route) }
                    )
                }
                composable(
                    route = Screen.AddEdit.route,
                    arguments = listOf(navArgument("workoutId") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
                ) { backStackEntry ->
                    val workoutId = backStackEntry.arguments?.getInt("workoutId")
                    AddEditScreen(
                        workoutId = if (workoutId == -1) null else workoutId,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.History.route) {
                    HistoryScreen(onNavigateBack = { navController.popBackStack() })
                }
                composable(Screen.Stats.route) {
                    StatsScreen(onNavigateBack = { navController.popBackStack() })
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(onNavigateBack = { navController.popBackStack() })
                }
                composable(Screen.BloodPressure.route) {
                    BloodPressureScreen(onNavigateBack = { navController.popBackStack() })
                }
            }
        }
    }
}
