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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import org.imaginativeworld.simplemvvm.network.ApiInterface
import org.imaginativeworld.simplemvvm.network.SafeApiRequest
import javax.inject.Inject

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


    // ----------------------------------------------------------------
    // Post
    // ----------------------------------------------------------------

    suspend fun getPosts() = withContext(Dispatchers.IO) {

        SafeApiRequest.apiRequest(context) {
            api.getPosts()
        }

    }

    suspend fun getPostsPaged(
        page: Int,
    ) = withContext(Dispatchers.IO) {

        SafeApiRequest.apiRequest(context) {
            api.getPostsPaged(
                page
            )
        }

    }

}