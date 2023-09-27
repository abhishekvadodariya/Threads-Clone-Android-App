package com.tech.threadsclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tech.threadsclone.screens.AddThreads
import com.tech.threadsclone.screens.BottomNav
import com.tech.threadsclone.screens.Home
import com.tech.threadsclone.screens.Login
import com.tech.threadsclone.screens.Notification
import com.tech.threadsclone.screens.Profile
import com.tech.threadsclone.screens.Register
import com.tech.threadsclone.screens.Serach
import com.tech.threadsclone.screens.Splash

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination = Routes.Splash.routes){

        composable(Routes.Splash.routes){
            Splash(navController)
        }

        composable(Routes.Home.routes){
            Home()
        }

        composable(Routes.AddThreads.routes){
            AddThreads()
        }

        composable(Routes.Notification.routes){
            Notification()
        }

        composable(Routes.BottomNav.routes){
            BottomNav(navController)
        }

        composable(Routes.Profile.routes){
            Profile()
        }

        composable(Routes.Search.routes){
            Serach()
        }

        composable(Routes.Login.routes){
            Login(navController)
        }

        composable(Routes.Register.routes){
            Register(navController)
        }
    }
}