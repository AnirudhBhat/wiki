package com.abhat.wiki.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.abhat.wiki.data.repository.WikiRepositoryImpl

@Suppress("UNCHECKED_CAST")
class WikiViewModelFactory(val wikiRepository: WikiRepositoryImpl): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WikiViewModel::class.java)) {
            return WikiViewModel(wikiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}