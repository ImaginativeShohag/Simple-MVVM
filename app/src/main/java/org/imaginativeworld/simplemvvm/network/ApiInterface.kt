/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.network

import org.imaginativeworld.simplemvvm.models.DemoPostResponse
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
    ): Response<DemoPostResponse>

    @GET("posts")
    suspend fun getPostsPaged(
        @Query("_format") format: String,
        @Query("access-token") accessToken: String,
        @Query("page") page: Long
    ): Response<DemoPostResponse>
}
