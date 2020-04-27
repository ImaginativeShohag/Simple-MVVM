package org.imaginativeworld.simplemvvm.utils

import android.content.res.Resources
import android.util.Patterns
import com.squareup.moshi.Moshi
import org.imaginativeworld.simplemvvm.network.ApiClient
import java.util.*

object Utils {

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
     * Get an instance of Moshi.
     */
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, ApiClient.DateJsonAdapter())
            .build()
    }

}