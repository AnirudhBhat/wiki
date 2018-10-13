package com.abhat.wiki.data.service

import com.abhat.wiki.data.model.Wiki
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface WikiApi {
    @GET("{title}")
    fun getWiki(@Path("title") title: String): Deferred<Wiki>
}