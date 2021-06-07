package org.imaginativeworld.simplemvvm.utils.extensions

import android.widget.Spinner


/**
 * Get item id for general spinner implementation.
 */
fun Spinner.getGeneralSpinnerSelectedItemId(): Int {
    return this.let {
        @Suppress("UNCHECKED_CAST")
        val selectedItem = it.selectedItem as Map<String, String>

        selectedItem["id"]?.toInt()
            ?: throw Exception("This spinner not using general spinner items!")
    }
}


/**
 * Get item name for general spinner implementation.
 */
fun Spinner.getGeneralSpinnerSelectedItemName(): String {
    return this.let {
        @Suppress("UNCHECKED_CAST")
        val selectedItem = it.selectedItem as Map<String, String>

        selectedItem["name"]
            ?: throw Exception("This spinner not using general spinner items!")
    }
}