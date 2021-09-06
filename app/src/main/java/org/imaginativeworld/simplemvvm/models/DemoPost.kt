package org.imaginativeworld.simplemvvm.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class DemoPost(
    @Json(name = "body")
    val body: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "user_id")
    val userId: String
)