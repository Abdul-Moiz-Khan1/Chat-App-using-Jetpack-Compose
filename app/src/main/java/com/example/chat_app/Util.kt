package com.example.chat_app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import java.lang.reflect.Modifier

fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route)
    {
        popUpToRoute
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressBar() {
    Row(modifier = androidx.compose.ui.Modifier
        .alpha(0.5f)
        .background(Color.Gray)
        .clickable { false }
        .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator()

    }

}

@Composable
fun CheckSignedIn(vm: chatting_ViewModel, navController: NavController) {
    val alreadySignedin = remember {
        mutableStateOf(false)
    }
    val signedin = vm.signin.value

}