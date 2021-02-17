package com.habib.jokes.ui.home.pagerviews.allJokes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.habib.jokes.models.Joke
import com.habib.jokes.models.Joke.Companion.toJoke
import com.habib.jokes.network.FirebaseSource
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class AllOtherJokeViewModel(
    private val repository: FirebaseSource
) : ViewModel() , EventListener<QuerySnapshot>  {

    private val _othersJokes = MutableLiveData<List<Joke?>>()

    val othersJokes : LiveData<List<Joke?>>
    get() = _othersJokes


    init {
        viewModelScope.launch {
            try {
                _othersJokes.value = repository.getOthersJokes()
            }catch (ex: Exception){
                Log.d("AllothersJokes", ex.message.toString())
            }
        }
    }

    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        _othersJokes.value = null
       _othersJokes.value = value?.documents?.mapNotNull { it.toJoke() }
    }
}