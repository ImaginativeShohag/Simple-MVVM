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
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.ui.screens.MainActivity
import timber.log.Timber

class CountService : Service() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var counter = 0

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // val data = intent?.getStringExtra("data")
        }
    }

    override fun onCreate() {
        Timber.d("onCreate")

        // Register Broadcast
        registerReceiver(
            receiver,
            IntentFilter(BROADCAST_KEY_COUNT)
        )
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")

        val isStopService = intent.extras?.getBoolean(BUNDLE_KEY_STOP_SERVICE) ?: false

        initNotificationAndStartForeground()

        if (isStopService) {
            stopSelf()
        } else {
            Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

            scope.launch {
                while (isActive) {
                    delay(1000)

                    counter += 1

                    // Broadcast the new value
                    Intent(BROADCAST_SERVICE_INTENT).also { intent ->
                        intent.putExtra(BROADCAST_KEY_COUNT, counter)
                        sendBroadcast(intent)
                    }

                    // Update the notification
                    val pendingIntentFlag =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        } else {
                            PendingIntent.FLAG_UPDATE_CURRENT
                        }

                    val pendingIntent: PendingIntent = Intent(
                        applicationContext,
                        MainActivity::class.java
                    ).let { notificationIntent ->

                        PendingIntent.getActivity(
                            applicationContext,
                            0,
                            notificationIntent,
                            pendingIntentFlag
                        )
                    }

                    // Create a notification builder
                    val builder =
                        NotificationCompat.Builder(applicationContext, CHANNEL_DEFAULT_IMPORTANCE)
                            .setContentTitle(getText(R.string.app_name))
                            .setContentText("Current count value is $counter")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pendingIntent)

                    val notificationManager = NotificationManagerCompat.from(applicationContext)

                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        notificationManager.notify(ONGOING_NOTIFICATION_ID, builder.build())
                    }
                }
            }
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    private fun initNotificationAndStartForeground() {
        // If the notification supports a direct reply action, use
        // PendingIntent.FLAG_MUTABLE instead.
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_DEFAULT_IMPORTANCE,
                "Count notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "All count notifications"
            channel.setSound(null, null)
            val notificationManager = NotificationManagerCompat.from(applicationContext)
            notificationManager.createNotificationChannel(channel)
        }

        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle(getText(R.string.app_name))
                .setContentText("Current count value is $counter")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build()

        // Notification ID cannot be 0.
        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent): IBinder? {
        Timber.d("onBind")

        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Timber.d("onDestroy")

        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()

        job.cancel()

        // Unregister Broadcast
        unregisterReceiver(receiver)
    }

    companion object {
        const val BROADCAST_SERVICE_INTENT = "org.imaginativeworld.simplemvvm.COUNT_SERVICE"
        const val BROADCAST_KEY_COUNT = "count"

        const val BUNDLE_KEY_STOP_SERVICE = "stop_service"

        const val ONGOING_NOTIFICATION_ID = 1
        const val CHANNEL_DEFAULT_IMPORTANCE = "default"
    }
}
