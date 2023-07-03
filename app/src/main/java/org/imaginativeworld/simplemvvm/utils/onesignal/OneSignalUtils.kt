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
