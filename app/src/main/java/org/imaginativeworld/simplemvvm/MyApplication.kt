package org.imaginativeworld.simplemvvm

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}