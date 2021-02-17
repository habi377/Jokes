package com.habib.jokes.ui.home.addJoke

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.jokes.models.Joke
import com.habib.jokes.network.FirebaseSource
import kotlinx.coroutines.launch

class JokeDialogViewModel(
    private val repository: FirebaseSource
) : ViewModel() {

    var joke :String?=null
    private val _navigateToSubmit = MutableLiveData<Boolean?>()
    private val _navigateToCancel = MutableLiveData<Boolean?>()

    val navigateToSubmit: LiveData<Boolean?>
        get() = _navigateToSubmit

    val navigateToCancel: LiveData<Boolean?>
        get() = _navigateToCancel


    fun onSubmit() {
        if(joke!=null){
            viewModelScope.launch {
                try {
                    repository.currentUser()?.let {user->
                        joke?.let {
                            repository.addJoke(Joke(user.uid,user.email!!,it,
                                downVote = false,
                                upVote = false,
                                stared = false
                            ))
                        }
                    }
                    _navigateToSubmit.value = true
                    Log.d("JokeViewModel","Success data Inserted")
                }catch (ex:Exception) {
                    Log.d("JokeViewModel","Ex "+ex.message)
                }
            }
        }else Log.d("JokeViewModel","Empty joke value")
    }

    fun onCancel() {
        _navigateToCancel.value = true
    }

    fun doneNavigation() {
        _navigateToCancel.value = false
        _navigateToSubmit.value = false
    }
}