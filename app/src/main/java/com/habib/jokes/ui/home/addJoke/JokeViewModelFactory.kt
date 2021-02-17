package com.habib.jokes.ui.home.addJoke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.habib.jokes.network.FirebaseSource

class JokeViewModelFactory(
    private val repository: FirebaseSource
) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeDialogViewModel::class.java)) {
            return JokeDialogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}