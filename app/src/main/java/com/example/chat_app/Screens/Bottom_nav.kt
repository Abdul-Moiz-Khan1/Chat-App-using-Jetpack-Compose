package com.example.chat_app.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chat_app.DestinationScreens
import com.example.chat_app.R
import com.example.chat_app.navigateTo

enum class Bottom_nav(val icon: Int, val nav_destination: DestinationScreens) {
    CHATLIST(R.drawable.chat, DestinationScreens.ChatList),
    STATUSLIST(R.drawable.status, DestinationScreens.Status),
    PROFILE(R.drawable.profile, DestinationScreens.Profile)

}

@Composable
fun Bottom_nav(selectedItem: Bottom_nav, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp)
            .background(Color.White)

    ) {
        for (item in Bottom_nav.entries) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .weight(1f)
                    .clickable {
                        navigateTo(navController, item.nav_destination.route)
                    }, colorFilter = if (item == selectedItem) {
                    ColorFilter.tint(Color.Black)
                } else {
                    ColorFilter.tint(Color.Gray)
                }

            )
        }
    }
}