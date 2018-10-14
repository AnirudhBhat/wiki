package com.abhat.wiki.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.ImageView
import com.abhat.wiki.data.model.Wiki
import com.abhat.wiki.data.repository.WikiRepository
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

class WikiViewModel(val wikiRepository: WikiRepository): ViewModel() {
    private val job = Job()
    private var wikiResult: MutableLiveData<Wiki> = MutableLiveData()

    fun fetchWiki(query: String, image: ImageView) {
        wikiResult.value?.let {
            // do nothing if we already have result
        } ?: run {
            runBlocking {
                try {
                    val wiki = async(job) {
                        wikiRepository.getWiki(query).await()
                    }.await()
                    wikiResult.value = wiki
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun cleanSearchTerm(query: String): String {
        var querySplit = query.split(" ")
        var cleanedSearchTerm: StringBuilder = java.lang.StringBuilder()
        querySplit.forEach {
            cleanedSearchTerm.append(it.capitalize())
            cleanedSearchTerm.append(" ")
        }
        /*if (querySplit.size > 1) {
            return querySplit[0].capitalize() + " " + querySplit[1].capitalize()
        }*/
        return cleanedSearchTerm.toString().trim()
    }

    fun getWikiResult(): MutableLiveData<Wiki> {
        return wikiResult
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}