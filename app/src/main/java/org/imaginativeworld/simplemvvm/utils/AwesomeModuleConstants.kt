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
