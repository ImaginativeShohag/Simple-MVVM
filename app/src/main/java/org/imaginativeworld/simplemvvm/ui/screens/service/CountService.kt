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

package org.imaginativeworld.simplemvvm.ui.screens.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.ui.screens.MainActivity
import org.imaginativeworld.simplemvvm.utils.Constants

class CountService : Service() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private lateinit var notification: NotificationCompat.Builder
    private var counter = 0

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val isStopService = intent.extras?.getBoolean(BUNDLE_KEY_STOP_SERVICE) ?: false

        makeForeground()

        if (isStopService) {
            stopSelf()
        } else {
            Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

            scope.launch {
                while (isActive) {
                    delay(1000)

                    counter += 1

                    // Broadcast the new value
                    Intent(Constants.BROADCAST_ACTION_COUNT_SERVICE).also { intent ->
                        intent.putExtra(BROADCAST_KEY_COUNT, counter)

                        LocalBroadcastManager.getInstance(applicationContext)
                            .sendBroadcast(intent)
                    }

                    // Update the notification
                    val notification = getNotificationBuilder(
                        content = "Current count value is $counter"
                    ).build()

                    val notificationManager = NotificationManagerCompat.from(applicationContext)

                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        notificationManager.notify(ONGOING_NOTIFICATION_ID, notification)
                    }
                }
            }
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    private fun makeForeground() {
        val notification = getNotificationBuilder(
            content = "Current count value is $counter"
        ).build()

        // Notification ID cannot be 0.
        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    private fun getNotificationBuilder(
        content: String
    ): NotificationCompat.Builder {
        if (this::notification.isInitialized) {
            notification.setContentText(content)
        } else {
            val pendingIntent: PendingIntent =
                Intent(this, MainActivity::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(
                        this,
                        0,
                        notificationIntent,
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        } else {
                            PendingIntent.FLAG_UPDATE_CURRENT
                        }
                    )
                }

            val notificationManager = NotificationManagerCompat.from(applicationContext)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                notificationManager
                    .getNotificationChannel(Constants.NOTIFICATION_CHANNEL_SERVICE_DEFAULT) == null
            ) {
                val channel = NotificationChannel(
                    Constants.NOTIFICATION_CHANNEL_SERVICE_DEFAULT,
                    "Service notification",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = "All service notifications"
                channel.setSound(null, null)
                notificationManager.createNotificationChannel(channel)
            }

            notification =
                NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_SERVICE_DEFAULT)
                    .setContentTitle(getText(R.string.app_name))
                    .setContentText(content)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
        }

        return notification
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()

        job.cancel()
    }

    companion object {
        const val BROADCAST_KEY_COUNT = "count"

        const val BUNDLE_KEY_STOP_SERVICE = "stop_service"

        const val ONGOING_NOTIFICATION_ID = 1
    }
}
