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
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    // ----------------------------------------------------------------
    // Posts
    // ----------------------------------------------------------------

    @GET("v1/posts")
    suspend fun getPosts(): Response<DemoPostResponse>

    @GET("v1/posts")
    suspend fun getPostsPaged(@Query("page") page: Int): Response<DemoPostResponse>

    // ----------------------------------------------------------------
    // Posts
    // ----------------------------------------------------------------

    @GET("todos")
    suspend fun getTodos(): Response<List<TodoItem>>

    @POST("todos")
    suspend fun addTodo(@Body todo: TodoItem): Response<TodoItem>

    @GET("todos/{id}")
    suspend fun getTodoDetails(@Path("id") id: Int): Response<TodoItem>

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): Response<Unit>

    @PUT("todos/{id}")
    suspend fun updateTodo(@Body todo: TodoItem): Response<TodoItem>
}
