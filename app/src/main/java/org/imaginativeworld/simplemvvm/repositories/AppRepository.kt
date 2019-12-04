package org.imaginativeworld.simplemvvm.repositories

import org.imaginativeworld.simplemvvm.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.imaginativeworld.simplemvvm.models.PostResponse
import org.imaginativeworld.simplemvvm.models.UserEntity
import org.imaginativeworld.simplemvvm.network.ApiInterface
import org.imaginativeworld.simplemvvm.network.SafeApiRequest

class AppRepository(
    private val api: ApiInterface,
    private val db: AppDatabase
) : SafeApiRequest() {

    // ----------------------------------------------------------------
    // User
    // ----------------------------------------------------------------

    suspend fun addUser(
        userModel: UserEntity
    ): Long {
        return db.userDao().addUser(userModel)
    }

    suspend fun getUsers(): List<UserEntity> {
        return db.userDao().getUsers()
    }


    // ----------------------------------------------------------------
    // Post
    // ----------------------------------------------------------------

    suspend fun getPosts(): List<PostResponse> {
        return withContext(Dispatchers.IO) {

            apiRequest {
                api.getPosts()
            }

        }
    }

}