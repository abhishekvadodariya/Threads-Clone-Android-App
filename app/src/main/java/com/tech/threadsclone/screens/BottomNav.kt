package com.tech.threadsclone.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tech.threadsclone.model.BottomNavItem
import com.tech.threadsclone.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: NavHostController) {
    val navControllerBottom: NavHostController = rememberNavController()

    Scaffold(bottomBar ={MyBottomBar(navControllerBottom)}) { innerPadding ->
        NavHost(navController = navControllerBottom, startDestination = Routes.Home.routes,
            modifier = Modifier.padding(innerPadding)){
            composable(route = Routes.Home.routes){
                Home()
            }
            composable(route = Routes.Notification.routes){
                Notification()
            }
            composable(route = Routes.Search.routes){
                Serach()
            }
            composable(route = Routes.AddThreads.routes){
                AddThreads()
            }
            composable(route = Routes.Profile.routes){
                Profile(navController)
            }
        }

    }
}

@Composable
fun MyBottomBar(navControllerBottom: NavHostController) {

    val backStackEntry = navControllerBottom.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem("Home", Routes.Home.routes, Icons.Rounded.Home),
        BottomNavItem("Search", Routes.Search.routes, Icons.Rounded.Search),
        BottomNavItem("Add Threads", Routes.AddThreads.routes, Icons.Rounded.Add),
        BottomNavItem("Notification", Routes.Notification.routes, Icons.Rounded.Notifications),
        BottomNavItem("Profile", Routes.Profile.routes, Icons.Rounded.Person),
    )

    BottomAppBar {
        list.forEach {
            val selected: Boolean = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(selected = selected, onClick = {navControllerBottom.navigate(it.route){
                popUpTo(navControllerBottom.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true
            } }, icon = {
                Icon(imageVector = it.icon, contentDescription = it.title)
            })
        }
    }
}