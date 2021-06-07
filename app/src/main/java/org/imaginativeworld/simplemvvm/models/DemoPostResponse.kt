package org.imaginativeworld.simplemvvm.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class DemoPostResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "data")
    val result: List<DemoPostResult>
)