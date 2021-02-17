package com.habib.jokes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.habib.jokes.network.FirebaseSource
import com.habib.jokes.ui.login.LoginViewModel

class HomeViewModelFactory(
    private val repository: FirebaseSource
) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}