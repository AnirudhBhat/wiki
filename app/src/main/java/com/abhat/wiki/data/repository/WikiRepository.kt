package com.abhat.wiki.data.repository

import com.abhat.wiki.data.model.Wiki
import kotlinx.coroutines.experimental.Deferred

interface WikiRepository {

    fun getWiki(title: String): Deferred<Wiki>
}