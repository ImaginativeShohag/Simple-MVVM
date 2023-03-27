package org.imaginativeworld.simplemvvm.models.awesometodos

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class TodoItem(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "title")
    val title: String,
    @Json(name = "completed")
    val completed: Boolean,
    @Json(name = "userId")
    val userId: Int
)
