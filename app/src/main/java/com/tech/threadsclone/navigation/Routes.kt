package com.tech.threadsclone.navigation

sealed class Routes(val routes: String){
    object Home: Routes("home")
    object AddThreads: Routes("AddThreads")
    object BottomNav: Routes("BottomNav")
    object Notification: Routes("Notification")
    object Profile: Routes("Profile")
    object Search: Routes("Search")
    object Splash: Routes("Splash")
    object Login: Routes("Login")
    object Register: Routes("Register")
}
