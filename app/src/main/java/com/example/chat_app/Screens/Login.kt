package com.example.chat_app.Screens

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chat_app.chatting_ViewModel


@Composable
fun Login(navController: NavController, vm : chatting_ViewModel) {


    Text(
        text = "Login Scren", modifier = Modifier
            .background(Color.Gray)
            .padding(
                top=400.dp
            )
            .fillMaxSize(), textAlign = TextAlign.Center

    )

}

