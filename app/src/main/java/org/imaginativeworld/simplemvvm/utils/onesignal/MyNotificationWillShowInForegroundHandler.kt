package org.imaginativeworld.simplemvvm.utils.onesignal

import android.app.Application
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal.OSNotificationWillShowInForegroundHandler
import org.imaginativeworld.simplemvvm.utils.Constants

// Runs before displaying a notification while the app is in focus. Use this handler to decide if the notification should show or not.
class MyNotificationWillShowInForegroundHandler(private val application: Application) :
    OSNotificationWillShowInForegroundHandler {
    override fun notificationWillShowInForeground(notificationReceivedEvent: OSNotificationReceivedEvent) {
        // Get custom additional data you sent with the notification
//        JSONObject data = notificationReceivedEvent.getNotification().getAdditionalData();

        // Complete with a notification means it will show
        notificationReceivedEvent.complete(notificationReceivedEvent.notification)

        // Send broadcast
        LocalBroadcastManager.getInstance(application.applicationContext)
            .sendBroadcast(Intent(Constants.BROADCAST_ACTION_NOTIFICATIONS))
    }
}