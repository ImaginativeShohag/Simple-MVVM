package org.imaginativeworld.simplemvvm.models.awesometodos

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class TodoItem(
    @Json(name = "due_on")
    val dueOn: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "status")
    val status: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "user_id")
    val userId: Int
)
