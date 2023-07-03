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

package org.imaginativeworld.simplemvvm.utils.onesignal

import com.onesignal.OneSignal
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

object OneSignalUtils {
    /**
     * Send test push notification to current user.
     */
    fun sendTestPushNotification() {
        OneSignal.getDeviceState()?.userId?.let { userId ->
            try {
                OneSignal.postNotification(
                    JSONObject(
                        """
{
    "contents": {
        "en":"You are special. ✨"
    },
    "headings": {
        "en":"Remember!"
    },
    "include_player_ids": ["$userId"]
}
                        """.trimMargin()
                    ),
                    object : OneSignal.PostNotificationResponseHandler {
                        override fun onSuccess(response: JSONObject) {
                            Timber.tag("OneSignalExample")
                                .i("postNotification Success: %s", response)
                        }

                        override fun onFailure(response: JSONObject) {
                            Timber.tag("OneSignalExample")
                                .e("postNotification Failure: %s", response)
                        }
                    }
                )
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}
