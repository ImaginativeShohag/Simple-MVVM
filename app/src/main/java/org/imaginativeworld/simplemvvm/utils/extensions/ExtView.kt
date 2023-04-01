package org.imaginativeworld.simplemvvm.utils.extensions

import android.view.View

/***
 * Show the view.
 */
fun View.show() {
    visibility = View.VISIBLE
}

/***
 * Hide the view.
 */
fun View.hide() {
    visibility = View.GONE
}
