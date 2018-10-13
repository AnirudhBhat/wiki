package com.abhat.wiki.di

import com.abhat.wiki.data.repository.WikiRepositoryImpl
import com.abhat.wiki.data.service.WikiApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { provideOkHttpClient()}
    single { provideRetrofit(get()) }
    factory { provideWikiApi(get()) }
    factory { provideWikiRepository(get()) }
}

private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl("https://en.wikipedia.org/api/rest_v1/page/summary/")
        .client(okHttpClient)
        .build()

private fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
}

private fun provideWikiApi(retrofit: Retrofit) = retrofit.create(WikiApi::class.java)

private fun provideWikiRepository(wikiApi: WikiApi) = WikiRepositoryImpl(wikiApi)