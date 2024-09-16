package com.example.chat_app

import android.os.Handler
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chat_app.data.Event
import com.example.chat_app.data.USER_NODE
import com.example.chat_app.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.navigation.compose.rememberNavController as rememberNavController1


@HiltViewModel
class chatting_ViewModel @Inject constructor(

    val auth: FirebaseAuth,
    val db: FirebaseFirestore

) : ViewModel() {

    var signin = mutableStateOf(false)
    var inProcess = mutableStateOf<Boolean>(false)
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    val UserData = mutableStateOf<UserData?>(null)

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
                    createOrUpdateProfile(name , number ,)
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
                    createOrUpdateProfile(name = name , number = number)
                    Log.d("Check report", "Logged in  ")
                } else {
                    handleException(task.exception, "Signup Failed")
                }
            }

        } catch (e: Exception) {
            Log.e("Check report", "Error while loggig in ", e)
        }

    }

    private fun createOrUpdateProfile(name: String?, number: String?, imageUrl: String? = null) {
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
        Log.d("Check report", "Live chat exception", exception)
        exception?.printStackTrace()
        val errorMsg = exception!!.localizedMessage
        val message = if (message.isEmpty()) errorMsg else message
        eventMutableState.value = Event(message)
        inProcess.value = false
    }
    fun login(email:String , password: String){
        if(email.isEmpty() or password.isEmpty()){
            handleException(message = "Please Fill in all the Fields")
            return
        }
        else{
            inProcess.value = true
            auth.signInWithEmailAndPassword(email , password).addOnCompleteListener{
                if(it.isSuccessful){
                    signin.value = true
                    inProcess.value = false
                    auth.currentUser?.uid?.let {
                        getUserData(it)
                    }

                }
                else{
                    handleException(it.exception , "Sign in Failed")
                }
            }
        }
    }


}
