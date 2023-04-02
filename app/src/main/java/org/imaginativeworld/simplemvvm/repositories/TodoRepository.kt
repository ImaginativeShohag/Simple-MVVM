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

package org.imaginativeworld.simplemvvm.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.oopsnointernet.utils.NoInternetUtils
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import org.imaginativeworld.simplemvvm.models.awesometodos.asEntity
import org.imaginativeworld.simplemvvm.models.awesometodos.asModel
import org.imaginativeworld.simplemvvm.network.SafeApiRequest
import org.imaginativeworld.simplemvvm.network.api.TodoApiInterface
import javax.inject.Inject

class TodoRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: TodoApiInterface,
    private val db: AppDatabase,
) {
    suspend fun getTodos(userId: Int) = withContext(Dispatchers.IO) {
        if (NoInternetUtils.isConnectedToInternet(context.applicationContext)) {
            // Online
            val todoItems = SafeApiRequest.apiRequest(context) {
                api.getTodos(userId)
            }

            todoItems?.let {
                db.todoDao().removeAll()

                db.todoDao().insertAll(todoItems.map { it.asEntity() })
            }

            todoItems
        } else {
            // Offline
            db.todoDao().getAll().map { it.asModel() }
        }
    }

    suspend fun addTodo(userId: Int, todo: TodoItem) = withContext(Dispatchers.IO) {
        val newTodo = SafeApiRequest.apiRequest(context) {
            api.addTodo(userId, todo)
        }

        newTodo?.let {
            db.todoDao().insert(it.asEntity())
        }

        newTodo
    }

    suspend fun getTodoDetails(todoId: Int) = withContext(Dispatchers.IO) {
        if (NoInternetUtils.isConnectedToInternet(context.applicationContext)) {
            // Online
            val todoItem = SafeApiRequest.apiRequest(context) {
                api.getTodoDetails(todoId)
            }

            todoItem?.let {
                db.todoDao().update(todoItem.asEntity())
            }

            todoItem
        } else {
            // Offline
            db.todoDao().getById(todoId)?.asModel()
        }
    }

    suspend fun deleteTodo(postId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.deleteTodo(postId)
        }
    }

    suspend fun updateTodo(id: Int, todo: TodoItem) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.updateTodo(id, todo)
        }
    }
}
