package com.habib.jokes.ui.home.pagerviews.allJokes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.habib.jokes.network.FirebaseSource

class AllOthersViewModelProvider(
    private val repository: FirebaseSource
) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllOtherJokeViewModel::class.java)) {
            return AllOtherJokeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}