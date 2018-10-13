package com.abhat.wiki

import android.app.Application
import com.abhat.wiki.di.appModule
import org.koin.android.ext.android.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}