package com.weatherapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.ui.page.HomePage
import com.weatherapp.ui.page.ListPage
import com.weatherapp.ui.page.MapPage
import com.weatherapp.viewmodel.MainViewModel

@Composable
fun MainNavHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController, startDestination = Route.Home) {
        composable<Route.Home> { HomePage(viewModel) }
        composable<Route.List> { ListPage(viewModel) }
        composable<Route.Map> { MapPage(viewModel) }
    }
}