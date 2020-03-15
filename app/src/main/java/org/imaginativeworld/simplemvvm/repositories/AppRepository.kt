package org.imaginativeworld.simplemvvm.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.simplemvvm.datasource.PostPagedDataSource
import org.imaginativeworld.simplemvvm.datasource.PostPagedDataSourceFactory
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.models.PostResponse
import org.imaginativeworld.simplemvvm.models.PostResult
import org.imaginativeworld.simplemvvm.models.UserEntity
import org.imaginativeworld.simplemvvm.network.ApiInterface
import org.imaginativeworld.simplemvvm.network.SafeApiRequest
import java.util.concurrent.Executors
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val context: Context,
    private val api: ApiInterface,
    private val db: AppDatabase
) {

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
    ): PostResponse {
        return withContext(Dispatchers.IO) {

            SafeApiRequest.apiRequest(context) {
                api.getPosts(
                    format,
                    accessToken
                )
            }

        }
    }

    fun getPostsPaged(
        format: String,
        accessToken: String,
        listener: OnDataSourceErrorListener
    ): LiveData<PagedList<PostResult>> {

        val config = PagedList.Config.Builder()
            .run {
                setEnablePlaceholders(true)
                setPrefetchDistance(4)
                build()
            }

        val executor = Executors.newFixedThreadPool(4)

        return PostPagedDataSourceFactory(
            context,
            format,
            accessToken,
            api,
            listener
        ).let {

            LivePagedListBuilder(
                it,
                config
            )
                .setFetchExecutor(executor)
                .build()

        }

    }


}