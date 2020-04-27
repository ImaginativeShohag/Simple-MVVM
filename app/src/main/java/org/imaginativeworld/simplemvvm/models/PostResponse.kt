package org.imaginativeworld.simplemvvm.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PostResponse(
    @Json(name = "_meta")
    val _meta: PostMeta,
    @Json(name = "result")
    val result: List<PostResult>
)