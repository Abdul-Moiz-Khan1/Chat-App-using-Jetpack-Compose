package com.example.chat_app

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class chatting_ViewModel @Inject constructor(

    val auth: FirebaseAuth

) : ViewModel() {

    init {

    }

    fun signup(name: String, email: String, number: String, password: String) {
        try {
            if(auth.createUserWithEmailAndPassword(email, password).isSuccessful){
                Log.d("Check report", "Logged in  ")
            }
            else{
                Log.d("Check report", "Not loggede in ")
            }

        } catch (e: Exception) {
            Log.e("Check report", "Error while loggig in ", e)
        }

    }
}
