package com.example.chat_app

import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chat_app.data.CHATS
import com.example.chat_app.data.ChatData
import com.example.chat_app.data.ChatUser
import com.example.chat_app.data.Event
import com.example.chat_app.data.USER_NODE
import com.example.chat_app.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import androidx.navigation.compose.rememberNavController as rememberNavController1


@HiltViewModel
class chatting_ViewModel @Inject constructor(

    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage

) : ViewModel() {

    val chats = mutableStateOf<List<ChatData>>(listOf())
    var signin = mutableStateOf(false)
    var inProcess = mutableStateOf<Boolean>(false)
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    val UserData = mutableStateOf<UserData?>(null)
    var inProcessChats = mutableStateOf(false)

    init {
        val currentuser = auth.currentUser
        signin.value = currentuser != null
        currentuser?.uid.let {
            getUserData(it.toString())
        }

    }


    fun signup(name: String, email: String, number: String, password: String) {
        inProcess.value = true
        if (name.isEmpty() or email.isEmpty() or number.isEmpty() or password.isEmpty()) {
            handleException(message = "Signup Failed")
            return
        } else {
            inProcess.value = true
            db.collection(USER_NODE).whereEqualTo("number", number).get().addOnSuccessListener {
                if (it.isEmpty) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                signin.value = true
                                createOrUpdateProfile(name, number)
                                Log.d("Check report", "Logged in  ")
                            } else {
                                handleException(task.exception, "Signup Failed")

                            }
                        }
                } else {
                    handleException(message = "Number already exists")
                    inProcess.value = false
                }
            }
        }
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signin.value = true
                    createOrUpdateProfile(name = name, number = number)
                    Log.d("Check report", "Logged in  ")
                } else {
                    handleException(task.exception, "Signup Failed")
                }
            }

        } catch (e: Exception) {
            Log.e("Check report", "Error while loggig in ", e)
        }
    }

    fun createOrUpdateProfile(name: String?, number: String?, imageUrl: String? = null) {
        var uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            userName = name ?: UserData.value?.userName.toString(),
            userNumber = number ?: UserData.value?.userNumber.toString(),
            imageUrl = imageUrl ?: UserData.value?.imageUrl.toString()
        )
        uid?.let {
            inProcess.value = true
            db.collection(USER_NODE).document(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    //update
                    db.collection(USER_NODE).document(uid).set(userData)
                    inProcess.value = false

                } else {
                    Log.d("Check report", "user created")
                    db.collection(USER_NODE).document(uid).set(userData)
                    inProcess.value = false
                    getUserData(uid)
                }
            }.addOnFailureListener {
                handleException(it, "Cannot retrieve user")
            }
        }
    }

    private fun getUserData(uid: String) {
        inProcess.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error, "Cannot Retrieve User")
            }
            if (value != null) {
                val user = value.toObject<UserData>()
                UserData.value = user
                inProcess.value = false
            }
        }
    }

    fun handleException(exception: Exception? = null, message: String) {
        Log.e("Check report", message, exception)
        exception?.printStackTrace()
        val errorMsg = exception!!.localizedMessage
        val message = if (message.isEmpty()) errorMsg else message
        eventMutableState.value = Event(message)
        inProcess.value = false
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() or password.isEmpty()) {
            handleException(message = "Please Fill in all the Fields")
            return
        } else {
            inProcess.value = true
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    signin.value = true
                    inProcess.value = false
                    auth.currentUser?.uid?.let {
                        getUserData(it)
                    }
                } else {
                    handleException(it.exception, "Sign in Failed")
                }
            }
        }
    }

    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri) {
            createOrUpdateProfile(null, null, imageUrl = it.toString())
        }

    }

    private fun uploadImage(uri: Uri, OnSuccess: (Uri) -> Unit) {
        inProcess.value = true
        val storageref = storage.reference
        val random_uid = UUID.randomUUID()
        val imageref = storageref.child("images/$random_uid")
        val uploadTask = imageref.putFile(uri).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                inProcess.value = false
                UserData.value?.imageUrl = uri.toString()

            }?.addOnFailureListener {
                handleException(it, "Failed to get Image")
            }
        }
    }

    fun logout() {
        auth.signOut()
        signin.value = false
        UserData.value = null
        eventMutableState.value = Event("logged out")

    }

    fun addChat(number: String) {
        if (number.isEmpty() or !number.isDigitsOnly()) {
            handleException(message = "number must contain numbers only")
        } else {
            db.collection(CHATS).where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("user1.number", number), Filter.equalTo(
                            "user2.number",
                            UserData.value?.userNumber
                        )
                    ),
                    Filter.and(
                        Filter.equalTo("user1.number", UserData.value?.userNumber), Filter.equalTo(
                            "user2.number",
                            number
                        )
                    )
                )
            ).get().addOnSuccessListener { it ->
                if (it.isEmpty) {
                    db.collection(USER_NODE).whereEqualTo("userNumber", number).get()
                        .addOnSuccessListener { data ->
                            if (data.isEmpty) {
                                handleException(message = "Number Not Found")
                            } else {
                                val chatPartners = data.toObjects<UserData>()[0]
                                val id = db.collection(CHATS).document().id
                                val chat =
                                    ChatData(
                                        chatId = id,
                                        user1 = ChatUser(
                                            UserData.value?.userId,
                                            UserData.value?.userName,
                                            UserData.value?.imageUrl,
                                            UserData.value?.userNumber
                                        ),
                                        user2 = ChatUser(
                                            chatPartners.userId,
                                            chatPartners.userName,
                                            chatPartners.imageUrl,
                                            chatPartners.userNumber
                                        )

                                    )
                                db.collection(CHATS).document(id).set(chat)
                            }
                        }
                        .addOnFailureListener {
                            handleException(it , "exception")
                        }

                } else {
                    handleException(message = "Chats already Exists")
                }
            }
        }

    }


}
