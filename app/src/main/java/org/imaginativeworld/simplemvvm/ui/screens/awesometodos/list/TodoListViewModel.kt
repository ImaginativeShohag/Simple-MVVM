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

package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.TodoRepository
import org.imaginativeworld.simplemvvm.repositories.UserRepository
import org.imaginativeworld.simplemvvm.utils.SharedPref
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val todoRepository: TodoRepository,
    private val sharedPref: SharedPref,
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

    private val _todoItems: MutableLiveData<List<TodoItem>> by lazy {
        MutableLiveData<List<TodoItem>>()
    }

    val todoItems: LiveData<List<TodoItem>?>
        get() = _todoItems

    // ----------------------------------------------------------------

    private val _eventSignOutSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventSignOutSuccess: LiveData<Boolean>
        get() = _eventSignOutSuccess

    // ----------------------------------------------------------------

    fun getTodos() = viewModelScope.launch {
        _eventShowLoading.value = true

        val user = sharedPref.getUser() ?: return@launch

        try {
            val response = todoRepository.getTodos(user.id)

            _todoItems.value = response
        } catch (e: ApiException) {
            _todoItems.value = listOf()

            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }

    fun signOut() = viewModelScope.launch {
        userRepository.signOut()
        sharedPref.reset()

        _eventSignOutSuccess.postValue(true)
    }
}
