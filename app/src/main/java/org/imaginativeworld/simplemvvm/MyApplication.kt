/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Simple MVVM
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Simple-MVVM
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
            MyNotificationWillShowInForegroundHandler(this),
        )
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setNotificationOpenedHandler(MyNotificationOpenedHandler(this))
    }
}
