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

package org.imaginativeworld.simplemvvm.ui.screens.cms.user.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.user.User
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.UserRepository

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val eventShowMessage: LiveData<String?>
        get() = _eventShowMessage

    // ----------------------------------------------------------------

    private val _eventShowLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventShowLoading: LiveData<Boolean>
        get() = _eventShowLoading

    // ----------------------------------------------------------------

    private val _user: MutableLiveData<User?> by lazy {
        MutableLiveData<User?>()
    }

    val user: LiveData<User?>
        get() = _user

    // ----------------------------------------------------------------

    private val _eventDeleteSuccess: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    val eventDeleteSuccess: LiveData<Boolean?>
        get() = _eventDeleteSuccess

    // ----------------------------------------------------------------

    fun getDetails(
        userId: Int
    ) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val user = repository.getUser(userId)

            _user.postValue(user)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }

    fun delete(userId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            repository.deleteUser(userId)

            _eventDeleteSuccess.postValue(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
