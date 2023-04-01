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
import org.imaginativeworld.simplemvvm.models.awesometodos.User
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
    // Post
    // ----------------------------------------------------------------

    @GET("v1/posts")
    suspend fun getPosts(): Response<DemoPostResponse>

    @GET("v1/posts")
    suspend fun getPostsPaged(@Query("page") page: Int): Response<DemoPostResponse>

    // ----------------------------------------------------------------
    // User
    // ----------------------------------------------------------------

    @GET("v2/users/{id}")
    suspend fun getUser(@Path("id") userId: Int): Response<User>

    @POST("v2/users")
    suspend fun signIn(@Body user: User): Response<User>

    // ----------------------------------------------------------------
    // Todos
    // ----------------------------------------------------------------

    @GET("v2/users/{userId}/todos")
    suspend fun getTodos(@Path("userId") userId: Int): Response<List<TodoItem>>

    @POST("v2/users/{userId}/todos")
    suspend fun addTodo(@Path("userId") userId: Int, @Body todo: TodoItem): Response<TodoItem>

    @GET("v2/todos/{id}")
    suspend fun getTodoDetails(@Path("id") id: Int): Response<TodoItem>

    @DELETE("v2/todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): Response<Unit>

    @PUT("v2/todos/{id}")
    suspend fun updateTodo(@Path("id") id: Int, @Body todo: TodoItem): Response<TodoItem>
}
