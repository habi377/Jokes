package com.habib.jokes.network

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.habib.jokes.models.Joke
import com.habib.jokes.models.Joke.Companion.toJoke
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val fireStore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    companion object{
        @Volatile
        private var INSTANCE: FirebaseSource? = null
        fun getInstance(): FirebaseSource {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = FirebaseSource()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    suspend fun login(email: String, password: String) : Task<AuthResult>? {
        var result :Task<AuthResult>? = null
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            result = it
        }.await()
        return result
    }

    suspend fun register(email: String, password: String) :Task<AuthResult>? {
        var result :Task<AuthResult>? = null
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            result = it
        }.await()
        return result
    }

    suspend fun addJoke(joke: Joke) :Boolean? {
        var res = false
        fireStore.collection("jokes")
            .add(joke)
            .addOnSuccessListener { documentReference ->
                res = true
            }
            .addOnFailureListener { e ->
                res = false
            }.await()
        return res
    }

    suspend fun getOthersJokes() :List<Joke>? {
        try {
            return fireStore.collection("jokes")
                .whereNotEqualTo("userId",currentUser()?.uid)
                .get().await()
                .documents.mapNotNull { it.toJoke() }
        }catch (ex: Exception){
            Log.d("AllothersJokes",ex.message.toString())
        }
        return emptyList()
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

}