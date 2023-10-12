package com.tech.threadsclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tech.threadsclone.screens.AddThreads
import com.tech.threadsclone.screens.BottomNav
import com.tech.threadsclone.screens.Home
import com.tech.threadsclone.screens.Login
import com.tech.threadsclone.screens.Notification
import com.tech.threadsclone.screens.OtherUsers
import com.tech.threadsclone.screens.Profile
import com.tech.threadsclone.screens.Register
import com.tech.threadsclone.screens.Search
import com.tech.threadsclone.screens.Splash

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination = Routes.Splash.routes){

        composable(Routes.Splash.routes){
            Splash(navController)
        }

        composable(Routes.Home.routes){
            Home(navController)
        }

        composable(Routes.AddThreads.routes){
            AddThreads(navController)
        }

        composable(Routes.Notification.routes){
            Notification()
        }

        composable(Routes.BottomNav.routes){
            BottomNav(navController)
        }

        composable(Routes.Profile.routes){
            Profile(navController)
        }

        composable(Routes.Search.routes){
            Search(navController)
        }

        composable(Routes.Login.routes){
            Login(navController)
        }

        composable(Routes.Register.routes){
            Register(navController)
        }

        composable(Routes.OtherUsers.routes){
            val data:String? = it.arguments!!.getString("data")
            OtherUsers(navController,data!!)
        }
    }
}