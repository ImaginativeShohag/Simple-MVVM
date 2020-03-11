package org.imaginativeworld.simplemvvm.datasource

import android.content.Context
import androidx.paging.DataSource
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.models.PostResult
import org.imaginativeworld.simplemvvm.network.ApiInterface

class PostPagedDataSourceFactory(
    private val context: Context,
    private val format: String,
    private val accessToken: String,
    private val api: ApiInterface,
    private val listener: OnDataSourceErrorListener
) : DataSource.Factory<Long, PostResult>() {
    override fun create(): DataSource<Long, PostResult> {
        return PostPagedDataSource(
            context,
            format,
            accessToken,
            api,
            listener
        )
    }
}