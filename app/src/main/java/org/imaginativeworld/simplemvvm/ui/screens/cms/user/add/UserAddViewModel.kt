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

package org.imaginativeworld.simplemvvm.ui.screens.cms.user.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.User
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.UserRepository
import org.imaginativeworld.simplemvvm.utils.SharedPref
import org.imaginativeworld.simplemvvm.utils.extensions.isValidEmail

@HiltViewModel
class UserAddViewModel @Inject constructor(
    private val repository: UserRepository,
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

    private val _eventAddUserSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventAddUserSuccess: LiveData<Boolean>
        get() = _eventAddUserSuccess

    // ----------------------------------------------------------------

    private fun isValid(
        name: String,
        email: String,
        gender: String
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

        return true
    }

    fun addUser(
        name: String,
        email: String,
        gender: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, gender)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            val newUser = repository.signIn(
                User(
                    0, // It will be ignored in the server.
                    name,
                    email,
                    gender,
                    "active"
                )
            )

            if (newUser != null) {
                sharedPref.setUser(newUser)

                _eventAddUserSuccess.postValue(true)
            } else {
                _eventShowMessage.postValue("Sign in failed!")
            }
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
