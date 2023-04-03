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

package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.TodoRepository
import org.imaginativeworld.simplemvvm.utils.SharedPref

@HiltViewModel
class TodoAddViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val sharedPref: SharedPref
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

    private val _eventSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val eventSuccess: LiveData<Boolean>
        get() = _eventSuccess

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

    fun add(
        title: String,
        dueDate: Date?,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(title, dueDate, status)) {
            return@launch
        }

        _eventShowLoading.value = true

        val user = sharedPref.getUser() ?: return@launch

        try {
            repository.addTodo(
                user.id,
                TodoItem(
                    title = title,
                    dueOn = dueDate,
                    status = status.lowercase()
                )
            )

            _eventSuccess.postValue(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
