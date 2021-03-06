package org.imaginativeworld.simplemvvm.datasource

import android.content.Context
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.runBlocking
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.models.DemoPostResult
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.network.ApiInterface
import org.imaginativeworld.simplemvvm.network.SafeApiRequest

class PostPagedDataSource(
    private val context: Context,
    private val format: String,
    private val accessToken: String,
    private val api: ApiInterface,
    private val listener: OnDataSourceErrorListener
) : PageKeyedDataSource<Long, DemoPostResult>() {
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, DemoPostResult>
    ) {

        runBlocking {

            try {

                val result = SafeApiRequest.apiRequest(context) {
                    api.getPostsPaged(
                        format,
                        accessToken,
                        1
                    )
                }

                callback.onResult(result.result, null, 2)

            } catch (e: ApiException) {
                e.printStackTrace()

                listener.onDataSourceError(e)
            }

        }

    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, DemoPostResult>) {

        runBlocking {

            try {

                val result = SafeApiRequest.apiRequest(context) {
                    api.getPostsPaged(
                        format,
                        accessToken,
                        params.key
                    )
                }

                callback.onResult(result.result, params.key + 1)

            } catch (e: ApiException) {
                e.printStackTrace()

                listener.onDataSourceError(e)
            }

        }

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, DemoPostResult>) {

    }
}