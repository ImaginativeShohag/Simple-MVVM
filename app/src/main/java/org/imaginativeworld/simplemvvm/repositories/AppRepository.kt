/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import org.imaginativeworld.simplemvvm.models.awesometodos.User
import org.imaginativeworld.simplemvvm.network.ApiInterface
import org.imaginativeworld.simplemvvm.network.SafeApiRequest

class AppRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiInterface,
    private val db: AppDatabase
) {

    // ----------------------------------------------------------------
    // User
    // ----------------------------------------------------------------

    suspend fun addUser(
        userModel: DemoUserEntity
    ): Long {
        return withContext(Dispatchers.IO) {
            db.userDao().addUser(userModel)
        }
    }

    suspend fun removeAllUsers() {
        return withContext(Dispatchers.IO) {
            db.userDao().removeAllUsers()
        }
    }

    suspend fun getUsers(): List<DemoUserEntity> {
        return withContext(Dispatchers.IO) {
            db.userDao().getUsers()
        }
    }

    suspend fun updateUser(user: DemoUserEntity) {
        return withContext(Dispatchers.IO) {
            db.userDao().updateUser(user)
        }
    }

    // ----------------------------------------------------------------
    // Post
    // ----------------------------------------------------------------

    suspend fun getPosts() = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getPosts()
        }
    }

    suspend fun getPostsPaged(
        page: Int
    ) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getPostsPaged(
                page
            )
        }
    }

    // ----------------------------------------------------------------
    // User
    // ----------------------------------------------------------------

    suspend fun getUser(userId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getUser(userId)
        }
    }

    suspend fun signIn(user: User) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.signIn(user)
        }
    }

    // ----------------------------------------------------------------
    // Todos
    // ----------------------------------------------------------------

    suspend fun getTodos(userId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getTodos(userId)
        }
    }

    suspend fun addTodo(userId: Int, todo: TodoItem) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.addTodo(userId, todo)
        }
    }

    suspend fun getTodoDetails(postId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getTodoDetails(postId)
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
