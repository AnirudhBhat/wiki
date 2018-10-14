package com.abhat.wiki.data.repository

import com.abhat.wiki.data.model.Wiki
import com.abhat.wiki.data.service.WikiApi
import kotlinx.coroutines.experimental.Deferred

open class WikiRepositoryImpl(private val wikiApi: WikiApi): WikiRepository {
    override fun getWiki(title: String): Deferred<Wiki> {
        return wikiApi.getWiki(title)
    }
}