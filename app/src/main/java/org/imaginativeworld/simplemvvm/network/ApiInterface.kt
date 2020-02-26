package org.imaginativeworld.simplemvvm.network

import org.imaginativeworld.simplemvvm.models.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    // ----------------------------------------------------------------
    // Posts
    // ----------------------------------------------------------------

    @GET("posts")
    suspend fun getPosts(
        @Query("_format") format: String,
        @Query("access-token") accessToken: String
    ): Response<PostResponse>

}