package com.example.chat_app.Screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_app.chatting_ViewModel

@Composable
fun ChatList(navController: NavController, vm: chatting_ViewModel) {

    Text(
        text = "Chat list will be Shown Here!",
        modifier = Modifier.fillMaxSize().wrapContentHeight(),
        textAlign = TextAlign.Center,
        fontSize = 38.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.DarkGray
    )
}
