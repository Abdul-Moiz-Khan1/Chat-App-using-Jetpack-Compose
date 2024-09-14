package com.example.chat_app.Screens

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_app.CheckSignedIn
import com.example.chat_app.CommonProgressBar
import com.example.chat_app.DestinationScreens
import com.example.chat_app.R
import com.example.chat_app.chatting_ViewModel
import com.example.chat_app.navigateTo


@Composable
fun Login(navController: NavController, vm: chatting_ViewModel) {


    CheckSignedIn(vm, navController)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var email_state = remember {
                mutableStateOf(TextFieldValue())
            }
            var password_state = remember {
                mutableStateOf(TextFieldValue())
            }

            Image(
                painter = painterResource(id = R.drawable.chat),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Signup",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(value = email_state.value, onValueChange = {
                email_state.value = it
            }, label = { Text(text = "Email") }, modifier = Modifier.padding(8.dp)
            )


            OutlinedTextField(value = password_state.value, onValueChange = {
                password_state.value = it
            }, label = { Text(text = "Password") }, modifier = Modifier.padding(8.dp)
            )
            Button(
                onClick = {
                    vm.login(email_state.value.text , password_state.value.text)

                }, modifier = Modifier.padding(8.dp)

            ) {
                Text(text = "Sign In")
            }
            Text(
                text = "New User? Go to SignUp",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, DestinationScreens.Signup.route)

                    })

        }
        if (vm.inProcess.value) {
            CommonProgressBar()
        }
    }
}



