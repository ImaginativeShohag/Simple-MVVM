package org.imaginativeworld.simplemvvm.ui.screens.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
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

        if (isStopService) {
            stopSelf()
        } else {
            Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

            scope.launch {
                while (isActive) {
                    delay(1000)

                    counter += 1

                    Intent(BROADCAST_SERVICE_INTENT).also { intent ->
                        intent.putExtra(BROADCAST_KEY_COUNT, counter)
                        sendBroadcast(intent)
                    }
                }
            }
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
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
    }
}
