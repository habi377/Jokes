package com.habib.jokes.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.auth.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Joke(val userId: String, //Document ID is actually the user id
                val email: String,
                val joke: String,
                val downVote: Boolean,
                val upVote: Boolean,
                val stared: Boolean) : Parcelable {

    companion object {
        fun DocumentSnapshot.toJoke(): Joke? {
            try {
                val email = getString("email")!!
                val joke = getString("joke")!!
                val downVote = getBoolean("downVote")!!
                val upVote = getBoolean("upVote")!!
                val stared = getBoolean("stared")!!
                return Joke(id, email, joke, downVote,upVote,stared)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting user profile", e)
                return null
            }
        }
        private const val TAG = "User"
    }
}