package org.imaginativeworld.simplemvvm.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException
import org.imaginativeworld.simplemvvm.models.DemoPost
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import retrofit2.HttpException


class PostPagingSource(
    private val repository: AppRepository,
) : PagingSource<Int, DemoPost>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DemoPost> {
        val pagePosition = params.key ?: 1

        return try {

            val response = repository.getPostsPaged(
                pagePosition
            )

            val result = response.data

            if (result == null) {
                LoadResult.Error(ApiException("No data returned!"))
            } else {
                val nextKey = if (result.isEmpty()) {
                    null
                } else {
                    pagePosition + 1
                }

                LoadResult.Page(
                    data = result,
                    prevKey = if (pagePosition == 1) null else pagePosition - 1,
                    nextKey = nextKey
                )
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: ApiException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, DemoPost>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
