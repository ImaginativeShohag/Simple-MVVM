package org.imaginativeworld.simplemvvm.repositories

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.models.PostResponse
import org.imaginativeworld.simplemvvm.models.UserEntity
import org.imaginativeworld.simplemvvm.network.ApiInterface
import org.imaginativeworld.simplemvvm.network.SafeApiRequest

class AppRepository(
    private val context: Context,
    private val api: ApiInterface,
    private val db: AppDatabase
) : SafeApiRequest() {

    // ----------------------------------------------------------------
    // User
    // ----------------------------------------------------------------

    suspend fun addUser(
        userModel: UserEntity
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

    suspend fun getUsers(): List<UserEntity> {
        return withContext(Dispatchers.IO) {

            db.userDao().getUsers()

        }
    }


    // ----------------------------------------------------------------
    // Post
    // ----------------------------------------------------------------

    suspend fun getPosts(
        format: String,
        accessToken: String
    ): List<PostResponse> {
        return withContext(Dispatchers.IO) {

            apiRequest(context) {
                api.getPosts(
                    format,
                    accessToken
                )
            }

        }
    }

}