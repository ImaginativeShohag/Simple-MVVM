/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.utils.extensions

import com.squareup.moshi.Moshi
import org.imaginativeworld.simplemvvm.network.ApiClient
import timber.log.Timber
import java.net.URLEncoder
import java.util.*

object MoshiUtil {
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, ApiClient.DateJsonAdapter())
            .build()
    }
}

inline fun <reified T> String?.getObjFromJson(): T? {
    if (this == null) return null

    Timber.e("getObjFromJson: $this")

    val jsonAdapter = MoshiUtil.getMoshi().adapter(T::class.java).lenient()

    return jsonAdapter.fromJson(this)
}

inline fun <reified T> T?.getJsonFromObj(): String? {
    if (this == null) return null

    Timber.e("getJsonFromObj: $this")

    val jsonAdapter = MoshiUtil.getMoshi().adapter(T::class.java).lenient()

    return jsonAdapter.toJson(this).urlEncode()
}

fun String.urlEncode(): String = URLEncoder.encode(this, "utf-8")
