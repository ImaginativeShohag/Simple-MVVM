package org.imaginativeworld.simplemvvm.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class DemoPostMeta(
    @Json(name = "code")
    val code: Int,
    @Json(name = "currentPage")
    val currentPage: Int,
    @Json(name = "message")
    val message: String,
    @Json(name = "pageCount")
    val pageCount: Int,
    @Json(name = "perPage")
    val perPage: Int,
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "totalCount")
    val totalCount: Int
)