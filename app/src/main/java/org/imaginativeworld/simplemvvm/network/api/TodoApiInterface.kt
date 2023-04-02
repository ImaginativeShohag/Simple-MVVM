/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Simple MVVM
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.network.api

import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApiInterface {
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
