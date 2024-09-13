package com.example.chat_app

import androidx.navigation.NavController

fun navigateTo(navController: NavController , route:String){
    navController.navigate(route)
    {
       popUpToRoute
        launchSingleTop = true
    }
}