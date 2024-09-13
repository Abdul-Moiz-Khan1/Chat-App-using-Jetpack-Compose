package com.example.chat_app.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chat_app.DestinationScreens
import com.example.chat_app.chatting_ViewModel

@Composable
fun SignUp(navController: NavController ,  vm : chatting_ViewModel) {
    Text(
        text = "Click to move to next screen",
        modifier = Modifier.padding(top=300.dp)
            .clickable
             {
            navController.navigate(DestinationScreens.Login.route)
        })
}