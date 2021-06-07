package org.imaginativeworld.simplemvvm.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class GeneralResponse(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "message")
    val message: String?
)