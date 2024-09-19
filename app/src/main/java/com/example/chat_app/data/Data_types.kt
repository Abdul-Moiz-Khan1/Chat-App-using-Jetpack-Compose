package com.example.chat_app.data

data class UserData(
    val userId: String? = "",
    val userName: String? = "",
    val userNumber: String? = "",
    var imageUrl: String? = "",


    ) {
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to userName,
        "number" to userNumber,
        "imageUrl" to imageUrl
    )
}

data class ChatData(
    val chatId: String? = "",
    val name: String? = "",
    val user1: ChatUser,
    val user2: ChatUser
)

data class ChatUser(
    val userId: String? = "",
    val name: String? = "",
    val imageUrl: String? = "",
    val number: String? = "",
)