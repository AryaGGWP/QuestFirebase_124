package com.example.pertemuan15.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan15.ui.home.detail.view.DestinasiDetail
import com.example.pertemuan15.ui.home.detail.view.DetailView
import com.example.pertemuan15.ui.home.pages.HomeScreen
import com.example.pertemuan15.ui.insert.pages.InsertMhsView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ){
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsert.route)
                },
            )
        }
        composable(DestinasiInsert.route){
            InsertMhsView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.navigate(DestinasiHome.route) }
            )
        }
        composable(
            route = "${DestinasiDetail.route}/{nim}",
            arguments = listOf(navArgument("nim") { type = NavType.StringType })
        ) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            DetailView(
                nim = nim,
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {}
            )
        }
    }
}

