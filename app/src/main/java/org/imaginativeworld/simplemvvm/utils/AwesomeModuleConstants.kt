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

/**
 * This is an example for writing multi-level constants.
 */
object AwesomeModuleConstants {
    // ----------------------------------------------------------------
    // Pattern 1
    // ----------------------------------------------------------------
    const val EVENT_PACKAGE_BUY = "event_package_buy"
    const val USER_PROPERTIES_PACKAGE_BUY = "event_package_buy"
    const val EVENT_PARAM_PACKAGE_BUY = "event_package_buy"

    // ----------------------------------------------------------------
    // Pattern 2
    // ----------------------------------------------------------------
    object Event {
        const val PACKAGE_BUY = "event_package_buy"

        object Param {
            const val PACKAGE_BUY = "event_package_buy"
        }
    }

    object UserProperty {
        const val PACKAGE_BUY = "event_package_buy"
    }
}

fun awesomeFunction() {
    AwesomeModuleConstants.EVENT_PACKAGE_BUY
    AwesomeModuleConstants.Event.PACKAGE_BUY

    AwesomeModuleConstants.USER_PROPERTIES_PACKAGE_BUY
    AwesomeModuleConstants.Event.Param.PACKAGE_BUY

    AwesomeModuleConstants.EVENT_PARAM_PACKAGE_BUY
    AwesomeModuleConstants.UserProperty.PACKAGE_BUY
}
