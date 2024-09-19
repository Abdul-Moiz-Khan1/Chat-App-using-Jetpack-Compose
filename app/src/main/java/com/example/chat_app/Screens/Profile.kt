package com.example.chat_app.Screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_app.CommonDivider
import com.example.chat_app.CommonImage
import com.example.chat_app.CommonProgressBar
import com.example.chat_app.DestinationScreens
import com.example.chat_app.chatting_ViewModel
import com.example.chat_app.navigateTo

@Composable
fun Profile(navController: NavController, vm: chatting_ViewModel) {

    val inprocess = vm.inProcess.value
    if (inprocess) {
        CommonProgressBar()
    } else {
        Column {
            val Userdata = vm.UserData.value
            var name by rememberSaveable {
                mutableStateOf(Userdata?.userName ?: "")
            }
            var number by rememberSaveable {
                mutableStateOf(Userdata?.userNumber ?: "")
            }
            var imageurl by rememberSaveable {
                mutableStateOf(Userdata?.imageUrl ?: "")
            }
            profileContent(
                name = name,
                number = number,
                imageurl = imageurl,
                vm = vm,
                custom_modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp),
                onNameChange = { name = it },
                OnNumberChange = { number = it },
                OnSave = {
                    vm.createOrUpdateProfile(name = name, number = number, imageurl)
                },
                onBack = { navigateTo(navController, DestinationScreens.ChatList.route) },
                onLogOut = {
                    vm.logout()
                    navigateTo(navController, DestinationScreens.Login.route)
                }
            )
            Bottom_nav(selectedItem = Bottom_nav.PROFILE, navController = navController)
        }
    }
}

@Composable
fun profileContent(
    name: String,
    number: String,
    imageurl: String,
    vm: chatting_ViewModel,
    custom_modifier: Modifier,
    onNameChange: (String) -> Unit,
    OnNumberChange: (String) -> Unit,
    onLogOut: () -> Unit,
    onBack: () -> Unit,
    OnSave: () -> Unit,
) {
    Column(modifier = Modifier.padding(28.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Back",
                modifier = Modifier.clickable {
                    onBack.invoke()
                }
            )
            Text(text = "Save",
                modifier = Modifier.clickable {
                    OnSave.invoke()
                })
        }
        CommonDivider()
        profile_image(imageurl, vm)
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                label = { Text(text = "Name") },
                value = name,
                onValueChange = onNameChange,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                label = { Text(text = "Number") },
                value = number,
                onValueChange = OnNumberChange,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )
        }
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Log Out", modifier = Modifier.clickable {
                onLogOut.invoke()
            })

        }

    }
}

@Composable
fun profile_image(ImageUrl: String?, vm: chatting_ViewModel) {
    var imageUrl = ""
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUrl = uri.toString()
            vm.UserData.value?.imageUrl = imageUrl
            vm.uploadProfileImage(uri)
        }
    }

    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                CommonImage(

                    image = ImageUrl ?: imageUrl
                )

            }
            Text(text = "Change Profile Picture")
        }
        if (vm.inProcess.value) {
            CommonProgressBar()
        }
    }
}

