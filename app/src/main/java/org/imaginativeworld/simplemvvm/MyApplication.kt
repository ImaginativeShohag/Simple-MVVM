/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm

import android.app.Application
import org.imaginativeworld.simplemvvm.di.AppModule
import org.imaginativeworld.simplemvvm.di.ApplicationGraph
import org.imaginativeworld.simplemvvm.di.DaggerApplicationGraph
import timber.log.Timber
import timber.log.Timber.DebugTree

class MyApplication : Application() {

    val appGraph: ApplicationGraph by lazy {
        DaggerApplicationGraph
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
