package com.example.chat_app.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
fun SignUp(navController: NavController, vm: chatting_ViewModel) {

    CheckSignedIn(vm , navController )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var name_state = remember {
                mutableStateOf(TextFieldValue())
            }
            var number_state = remember {
                mutableStateOf(TextFieldValue())
            }
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
            OutlinedTextField(value = name_state.value, onValueChange = {
                name_state.value = it
            }, label = { Text(text = "Name") }, modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(value = email_state.value, onValueChange = {
                email_state.value = it
            }, label = { Text(text = "Email") }, modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(value = number_state.value, onValueChange = {
                number_state.value = it
            }, label = { Text(text = "Number") }, modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(value = password_state.value, onValueChange = {
                password_state.value = it
            }, label = { Text(text = "Password") }, modifier = Modifier.padding(8.dp)
            )
            Button(
                onClick = {
                    vm.signup(
                        name_state.value.text,
                        email_state.value.text,
                        number_state.value.text,
                        password_state.value.text
                    )
                }, modifier = Modifier.padding(8.dp)

            ) {
                Text(text = "Sign Up")
            }
            Text(
                text = "Already Signed Up?",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, DestinationScreens.Login.route)

                    })

        }
        if (vm.inProcess.value) {
            CommonProgressBar()
        }
        if (vm.signin.value) {
            navigateTo(navController, DestinationScreens.ChatList.route)

        }
    }

}
