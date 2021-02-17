package com.habib.jokes.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habib.jokes.network.FirebaseSource

class HomeViewModel(
    private val repository: FirebaseSource
) : ViewModel()  {

    private val _navigateToAuth = MutableLiveData<Boolean?>()
    private val _navigateToAddDialog = MutableLiveData<Boolean?>()

    val navigateToAuth: LiveData<Boolean?>
        get() = _navigateToAuth

    val navigateToaddDialog: LiveData<Boolean?>
        get() = _navigateToAddDialog


    fun onLogout(){
         repository.logout()
        _navigateToAuth.value = true
    }

    fun onAddClick(){
        _navigateToAddDialog.value = true
    }

    fun doneNavigation(){
        _navigateToAuth.value = false
    }
}