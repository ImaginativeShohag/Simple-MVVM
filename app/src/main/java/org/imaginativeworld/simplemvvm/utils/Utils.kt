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

package org.imaginativeworld.simplemvvm.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.media.RingtoneManager
import android.os.Build
import android.util.Patterns
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.imaginativeworld.simplemvvm.R

object Utils {

    // --------------------------------
    // Notifications
    // --------------------------------
    private var notificationId = 1

    /**
     * Get device screen width in pixel.
     */
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * Get device screen height in pixel.
     */
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    /**
     * Check if given text is valid email address.
     */
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    /**
     * Create and show a notification.
     *
     * Usage:
     * val targetIntent = Intent(this, XyzActivity::class.java)
     * targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
     *
     * Utils.showNotification(
     *     this,
     *     it.title,
     *     it.body,
     *     targetIntent
     * )
     */
    fun showNotification(
        context: Context,
        messageTitle: String?,
        messageBody: String,
        targetIntent: Intent?
    ) {
        val channelId = context.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(messageTitle ?: context.getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

        targetIntent?.also {
            val pendingIntent = PendingIntent.getActivity(
                context,
                0, // Request code
                targetIntent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
            )

            notificationBuilder.setContentIntent(pendingIntent)
        }

        val notificationManager = NotificationManagerCompat.from(context)

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "General notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "All general notifications"
            notificationManager.createNotificationChannel(channel)
        }

        if (notificationId == Int.MAX_VALUE) {
            notificationId = 1
        }

        notificationId++

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(notificationId, notificationBuilder.build())
        }
    }

    /**
     * A default try-catch block to handle general crashes.
     */
    inline fun ignoreCrash(
        couldBeCrash: () -> Unit
    ) {
        try {
            couldBeCrash()
        } catch (e: Exception) {
            /* no-op */
        }
    }
}
