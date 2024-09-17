package com.example.chat_app.Screens

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_app.CommonDivider
import com.example.chat_app.CommonProgressBar
import com.example.chat_app.chatting_ViewModel

@Composable
fun Profile(navController: NavController, vm: chatting_ViewModel) {

    val inprocess = vm.inProcess.value
    if (inprocess) {
        CommonProgressBar()
    }
    Column(modifier = Modifier.padding(top = 28.dp)) {
//        profileContent()
        Bottom_nav(selectedItem = Bottom_nav.PROFILE, navController = navController)
    }


}

@Composable
fun profileContent(onBack: () -> Unit, OnSave: () -> Unit) {
    Column {
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
                },
            )
            Text(text = "Save",
                modifier = Modifier.clickable {
                    OnSave.invoke()
                })
            CommonDivider()
            profile_image()
        }
    }
}

@Composable
fun profile_image(ImageUrl: String?, vm: chatting_ViewModel) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            vm.uploadProfileImage(uri)
        }
    }

    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                launcher.launch("image/*")
            }, horizontalAlignment = Alignment.CenterHorizontally) {
            Card(shape = CircleShape , modifier = Modifier.padding(8.dp).size(100.dp)){


            }

        }

    }
}

