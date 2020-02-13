package com.primasantosa.android.redditreader

import android.app.Application
import timber.log.Timber

class RedditReaderApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}