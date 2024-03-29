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

import org.imaginativeworld.simplemvvm.BuildConfig

object Constants {
    /**
     * Server endpoint without end slash.
     */
    // const val SERVER_ENDPOINT = "http://jsonplaceholder.typicode.com"
    const val SERVER_ENDPOINT = "https://gorest.co.in/public"

    /**
     * For MyNotificationOpenedHandler
     */
    const val INTENT_EXTRA_TARGET_KEY = "target"
    const val INTENT_EXTRA_TARGET_VAL_NOTIFICATIONS = "notifications"

    /**
     * For Broadcast
     */
    const val BROADCAST_ACTION_NOTIFICATIONS = BuildConfig.APPLICATION_ID + ".notifications"
    const val BROADCAST_ACTION_COUNT_SERVICE = BuildConfig.APPLICATION_ID + ".count_service"

    /**
     * Notification Channel
     */
    const val NOTIFICATION_CHANNEL_SERVICE_DEFAULT = "service_default"
}
