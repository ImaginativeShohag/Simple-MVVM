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
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import org.imaginativeworld.simplemvvm.utils.onesignal.MyNotificationOpenedHandler
import org.imaginativeworld.simplemvvm.utils.onesignal.MyNotificationWillShowInForegroundHandler
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        private const val ONESIGNAL_APP_ID = "4b51d526-b5e1-433a-87d6-f07c2490e0e9"
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        OneSignal.setNotificationWillShowInForegroundHandler(
            MyNotificationWillShowInForegroundHandler(this)
        )
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setNotificationOpenedHandler(MyNotificationOpenedHandler(this))
    }
}
