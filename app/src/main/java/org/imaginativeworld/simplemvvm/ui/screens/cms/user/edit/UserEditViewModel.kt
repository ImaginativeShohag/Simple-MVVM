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

package org.imaginativeworld.simplemvvm.ui.screens.cms.user.edit

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
import org.imaginativeworld.simplemvvm.utils.extensions.isValidEmail

@HiltViewModel
class UserEditViewModel @Inject constructor(
    private val repository: UserRepository
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

    private val _user: MutableLiveData<User?> by lazy {
        MutableLiveData<User?>()
    }

    val user: LiveData<User?>
        get() = _user

    // ----------------------------------------------------------------

    private val _eventUpdateSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventUpdateSuccess: LiveData<Boolean>
        get() = _eventUpdateSuccess

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

    // ----------------------------------------------------------------

    private fun isValid(
        name: String,
        email: String,
        gender: String,
        status: String
    ): Boolean {
        if (name.isBlank()) {
            _eventShowMessage.postValue("Please enter your name!")
            return false
        }

        if (email.isBlank()) {
            _eventShowMessage.postValue("Please enter your email!")
            return false
        }

        if (!email.isValidEmail()) {
            _eventShowMessage.postValue("Please enter a valid email!")
            return false
        }

        if (gender.isBlank()) {
            _eventShowMessage.postValue("Please select your gender!")
            return false
        }

        if (status.isBlank()) {
            _eventShowMessage.postValue("Please select user status!")
            return false
        }

        return true
    }

    fun update(
        userId: Int,
        name: String,
        email: String,
        gender: String,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, gender, status)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            repository.updateUser(
                userId,
                User(
                    userId,
                    name,
                    email,
                    gender,
                    status
                )
            )

            _eventUpdateSuccess.postValue(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
