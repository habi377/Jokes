package com.habib.jokes.network

class UserRepository (
    private val firebase: FirebaseSource
){
    suspend fun login(email: String, password: String) = firebase.login(email, password)

    suspend fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}