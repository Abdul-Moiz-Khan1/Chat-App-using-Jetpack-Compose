package com.example.chat_app.data

data class UserData(
    val userId: String? = "",
    val userName: String? = "",
    val userNumber: String? = "",
    var imageUrl: String? = "",


){
    fun toMap() = mapOf("userId" to userId , "name" to userName , "number" to userNumber , "imageUrl" to imageUrl)
}