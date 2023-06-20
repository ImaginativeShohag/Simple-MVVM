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

package org.imaginativeworld.simplemvvm.ui.screens.cms.comment.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.Comment
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.CommentRepository
import org.imaginativeworld.simplemvvm.utils.extensions.isValidEmail

@HiltViewModel
class CommentEditViewModel @Inject constructor(
    private val repository: CommentRepository
) : ViewModel() {

    private val _eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val eventShowMessage: LiveData<String?>
        get() = _eventShowMessage

    // ----------------------------------------------------------------

    private val _eventShowLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    val eventShowLoading: LiveData<Boolean?>
        get() = _eventShowLoading

    // ----------------------------------------------------------------

    private val _comment: MutableLiveData<Comment?> by lazy {
        MutableLiveData<Comment?>()
    }

    val comment: LiveData<Comment?>
        get() = _comment

    // ----------------------------------------------------------------

    private val _eventUpdateSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventUpdateSuccess: LiveData<Boolean>
        get() = _eventUpdateSuccess

    // ----------------------------------------------------------------

    fun getDetails(commentId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val comment = repository.getComment(commentId)

            _comment.postValue(comment)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }

    // ----------------------------------------------------------------

    private fun isValid(
        name: String,
        email: String,
        body: String
    ): Boolean {
        if (name.isBlank()) {
            _eventShowMessage.postValue("Please enter name!")
            return false
        }

        if (email.isBlank()) {
            _eventShowMessage.postValue("Please enter email!")
            return false
        }

        if (!email.isValidEmail()) {
            _eventShowMessage.postValue("Please enter a valid email!")
            return false
        }

        if (body.isBlank()) {
            _eventShowMessage.postValue("Please enter body!")
            return false
        }

        return true
    }

    fun update(
        postId: Int,
        commentId: Int,
        name: String,
        email: String,
        body: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, body)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            repository.updateComment(
                commentId,
                Comment(
                    id = commentId,
                    postId = postId,
                    name = name,
                    email = email,
                    body = body
                )
            )

            _eventUpdateSuccess.postValue(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
