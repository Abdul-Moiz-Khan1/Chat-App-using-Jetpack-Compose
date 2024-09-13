package com.example.chat_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.chat_app.Screens.Login
import com.example.chat_app.Screens.SignUp
import com.example.chat_app.ui.theme.Chat_appTheme
import dagger.hilt.android.lifecycle.HiltViewModel

sealed class DestinationScreens(var route: String) {
    object Signup : DestinationScreens("signup")
    object Login : DestinationScreens("login")
    object Profile : DestinationScreens("profile")
    object Status : DestinationScreens("status")
    object SingleChat : DestinationScreens("singleChat/{chatid}") {
        fun createRoute(id: String) = "singleChat/$id"
    }

    object ChatList : DestinationScreens("chatList")
    object SingleStatus : DestinationScreens("singleStatus/{userId}") {
        fun createRoute(userId: String) = "singleStatus/$userId"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        val vm = hiltViewModel<chatting_ViewModel>()
        NavHost(navController =navController , startDestination = DestinationScreens.Signup.route){
            composable(DestinationScreens.Signup.route){
                SignUp(navController , vm)
            }
            composable(DestinationScreens.Login.route){
                Login(navController , vm)
            }
        }

    }


}

