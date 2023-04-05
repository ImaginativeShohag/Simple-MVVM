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

package org.imaginativeworld.simplemvvm.ui.screens.cms.todo.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.todo.TodoItem
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.TodoRepository

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val repository: TodoRepository
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

    private val _todo: MutableLiveData<TodoItem?> by lazy {
        MutableLiveData<TodoItem?>()
    }

    val todo: LiveData<TodoItem?>
        get() = _todo

    // ----------------------------------------------------------------

    private val _eventUpdateSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventUpdateSuccess: LiveData<Boolean>
        get() = _eventUpdateSuccess

    // ----------------------------------------------------------------

    fun getDetails(todoId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val todo = repository.getTodo(todoId)

            _todo.postValue(todo)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }

    // ----------------------------------------------------------------

    private fun isValid(
        title: String,
        dueDate: Date?,
        status: String
    ): Boolean {
        if (title.isBlank()) {
            _eventShowMessage.postValue("Please enter title!")
            return false
        }

        if (status.isBlank()) {
            _eventShowMessage.postValue("Please select status!")
            return false
        }

        if (dueDate == null) {
            _eventShowMessage.postValue("Please select due date!")
            return false
        }

        return true
    }

    fun update(
        userId: Int,
        todoId: Int,
        title: String,
        dueDate: Date,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(title, dueDate, status)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            repository.updateTodo(
                todoId,
                TodoItem(
                    userId = userId,
                    title = title,
                    dueOn = dueDate,
                    status = status.lowercase()
                )
            )

            _eventUpdateSuccess.postValue(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
