package com.example.chat_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter


fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route)
    {
        popUpToRoute
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressBar() {
    Row(modifier = androidx.compose.ui.Modifier
        .alpha(0.5f)
        .background(Color.Gray)
        .clickable { false }
        .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator()

    }

}

@Composable
fun CheckSignedIn(vm: chatting_ViewModel, navController: NavController) {
    val alreadySignedin = remember {
        mutableStateOf(false)
    }
    val signedin = vm.signin.value
    if (signedin && !alreadySignedin.value) {
        alreadySignedin.value = true
        navController.navigate(DestinationScreens.ChatList.route) {
            popUpTo(0)
        }
    }

}

@Composable
fun CommonDivider() {
    HorizontalDivider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun CommonImage(
    image: String?,
    custom_modifier: Modifier = Modifier.wrapContentSize(),
    custom_contentScale: ContentScale = ContentScale.Crop
) {
    val painter = rememberImagePainter(data = image)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = custom_modifier,
        contentScale = custom_contentScale
    )

}