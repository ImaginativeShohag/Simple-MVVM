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
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.simplemvvm.datasource.PostPagedDataSourceFactory
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.models.DemoPostResponse
import org.imaginativeworld.simplemvvm.models.DemoPostResult
import org.imaginativeworld.simplemvvm.models.DemoUserEntity
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

    suspend fun getPosts(
        format: String,
        accessToken: String
    ): DemoPostResponse {
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
    ): LiveData<PagedList<DemoPostResult>> {

        val config = PagedList.Config.Builder()
            .run {
                setEnablePlaceholders(false)
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