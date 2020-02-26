package org.imaginativeworld.simplemvvm.models

data class PostResponse(
    val _meta: PostMeta,
    val result: List<PostResult>
)