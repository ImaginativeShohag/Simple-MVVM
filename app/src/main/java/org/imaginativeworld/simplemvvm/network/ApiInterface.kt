package org.imaginativeworld.simplemvvm.network

import org.imaginativeworld.simplemvvm.models.PostResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    // ----------------------------------------------------------------
    // Posts
    // ----------------------------------------------------------------

    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse>>


}