/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Simple MVVM
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.simplemvvm.models.Post
import org.imaginativeworld.simplemvvm.network.SafeApiRequest
import org.imaginativeworld.simplemvvm.network.api.PostApiInterface

class PostRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: PostApiInterface
) {
    // ----------------------------------------------------------------
    // Version 1
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
    // Version 2
    // ----------------------------------------------------------------

    suspend fun getPosts(userId: Int, page: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getPosts(userId, page)
        }
    }

    suspend fun addPost(userId: Int, post: Post) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.addPost(userId, post)
        }
    }

    suspend fun getPost(todoId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getPost(todoId)
        }
    }

    suspend fun deletePost(postId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.deletePost(postId)
        }
    }

    suspend fun updatePost(id: Int, post: Post) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.updatePost(id, post)
        }
    }
}
