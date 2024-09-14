package com.example.chat_app

import android.os.Handler
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.chat_app.data.Event
import com.example.chat_app.data.USER_NODE
import com.example.chat_app.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class chatting_ViewModel @Inject constructor(

    val auth: FirebaseAuth,
    val db: FirebaseFirestore

) : ViewModel() {

    init {

    }

    var inProcess = mutableStateOf<Boolean>(false)
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    var signin = mutableStateOf(false)
    val UserData = mutableStateOf<UserData?>(null)

    fun signup(name: String, email: String, number: String, password: String) {
        inProcess.value = true
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signin.value = true
//                    createOrUpdateProfile(name , number)
                    Log.d("Check report", "Logged in  ")
                } else {
                    hanldeException(task.exception, "Signup Failed")
                }
            }

        } catch (e: Exception) {
            Log.e("Check report", "Error while loggig in ", e)
        }

    }

    private fun createOrUpdateProfile(name: String?, number: String?, imageUrl: String?) {
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
                } else {
                    db.collection(USER_NODE).document(uid).set(userData)
                    inProcess.value = false
                    getUserData(uid)
                }
            }.addOnFailureListener {
                hanldeException(it, "Cannot retrieve user")
            }
        }

    }

    private fun getUserData(uid: String) {
        inProcess.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->
            if(error != null){
                hanldeException(error , "Cannot Retrieve User")
            }
            if (value != null) {
                val user = value.toObject<UserData>()
                UserData.value = user
                inProcess.value = false
            }
        }
    }

    private fun hanldeException(exception: Exception? = null, message: String) {
        Log.d("Check report", "Live chat exception", exception)
        exception?.printStackTrace()
        val errorMsg = exception!!.localizedMessage
        val message = if (message.isEmpty()) errorMsg else message
        eventMutableState.value = Event(message)
        inProcess.value = false
    }



}
