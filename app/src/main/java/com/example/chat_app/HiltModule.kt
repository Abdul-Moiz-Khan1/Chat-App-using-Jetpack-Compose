package com.example.chat_app

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class HiltModule {
    @Provides
    fun provideFirebaseAuth():FirebaseAuth = Firebase.auth

    @Provides
    fun provideFireStore() :FirebaseFirestore = Firebase.firestore
}