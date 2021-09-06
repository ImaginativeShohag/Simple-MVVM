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

    @GET("v1/posts")
    suspend fun getPosts(): Response<DemoPostResponse>

    @GET("v1/posts")
    suspend fun getPostsPaged(@Query("page") page: Int): Response<DemoPostResponse>
}
