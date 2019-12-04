package org.imaginativeworld.simplemvvm.utils

// A generic class that contains data and status about loading this data.
// Source: https://developer.android.com/jetpack/docs/guide#addendum
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
//    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
