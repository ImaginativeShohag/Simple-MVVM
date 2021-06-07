package org.imaginativeworld.simplemvvm.datasource

import android.content.Context
import androidx.paging.DataSource
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.models.DemoPostResult
import org.imaginativeworld.simplemvvm.network.ApiInterface

class PostPagedDataSourceFactory(
    private val context: Context,
    private val format: String,
    private val accessToken: String,
    private val api: ApiInterface,
    private val listener: OnDataSourceErrorListener
) : DataSource.Factory<Long, DemoPostResult>() {
    override fun create(): DataSource<Long, DemoPostResult> {
        return PostPagedDataSource(
            context,
            format,
            accessToken,
            api,
            listener
        )
    }
}