package com.example.chat_app.Screens

import android.app.Dialog
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import okhttp3.internal.wait

@Composable
fun ChatList(navController: NavController, vm: chatting_ViewModel) {

    if (vm.inProcess.value) {
        CommonProgressBar()
    } else {

    }

    Bottom_nav(selectedItem = Bottom_nav.CHATLIST, navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
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
