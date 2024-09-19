package com.example.chat_app.Screens

import android.app.Dialog
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_app.CommonProgressBar
import com.example.chat_app.chatting_ViewModel
import com.example.chat_app.titleText
import okhttp3.internal.wait

@Composable
fun ChatList(navController: NavController, vm: chatting_ViewModel) {

    val inPogress = vm.inProcessChats
    if (vm.inProcess.value) {
        CommonProgressBar()
    } else {
        val chats = vm.chats.value
        val userdata = vm.UserData.value
        val showDialog = remember {
            mutableStateOf(false)
        }
        val onFabClick: () -> Unit = {
            showDialog.value = true
        }
        val onDismiss: () -> Unit = { showDialog.value = false }
        val onAddChat: (String) -> Unit = {
            vm.addChat(it)
            showDialog.value = false
        }

        Scaffold(floatingActionButton = {
            fab(
                showDialog = showDialog.value,
                fabClick = { onFabClick },
                onDismiss = { onDismiss },
                onAddChat = { onAddChat }
            )
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                titleText(txt = "Chats")
                if (chats.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "No Chats Available")
                    }
                }

                Bottom_nav(selectedItem = Bottom_nav.CHATLIST, navController = navController)
            }
        })

    }

}

@Composable
fun fab(
    showDialog: Boolean,
    fabClick: () -> Unit,
    onDismiss: () -> Unit,
    onAddChat: (String) -> Unit
) {
    val addChatNumber = remember {
        mutableStateOf("")
    }
    if (showDialog) {
        AlertDialog(onDismissRequest = {
            onDismiss.invoke()
            addChatNumber.value = ""
        }, confirmButton = {
            Button(onClick = { onAddChat(addChatNumber.value) }) {
                Text(text = "Add Chat")
            }
        }, title = { Text(text = "Add Chat") }, text = {
            OutlinedTextField(
                value = addChatNumber.value,
                onValueChange = { addChatNumber.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        })

    } else {
        FloatingActionButton(
            onClick = { fabClick },
            containerColor = MaterialTheme.colorScheme.secondary,
            shape = CircleShape,
            modifier = Modifier.padding(bottom = 40.dp)
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)

        }
    }
}
