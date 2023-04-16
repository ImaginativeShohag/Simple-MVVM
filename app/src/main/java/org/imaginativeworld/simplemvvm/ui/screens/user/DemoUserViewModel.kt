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

package org.imaginativeworld.simplemvvm.ui.screens.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.user.UserEntity
import org.imaginativeworld.simplemvvm.usecase.UserUseCase
import timber.log.Timber

@HiltViewModel
class DemoUserViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val totalData = 4

    private val names = listOf(
        "Shohag",
        "Rakib",
        "Shafee",
        "Imran"
    )

    private val phones = listOf(
        "01111111111",
        "02222222222",
        "03333333333",
        "04444444444"
    )

    // ----------------------------------------------------------------

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

    fun clearUserObservables() {
        _userItems.value = null
    }

    // ----------------------------------------------------------------

    fun addUser() {
        viewModelScope.launch {
            // Create new user
            _eventShowLoading.value = true

            val randomIndex = Random.nextInt(totalData)

            val insertUserId = userUseCase.addUser(
                UserEntity(
                    name = names[randomIndex],
                    phone = phones[randomIndex],
                    image = "https://picsum.photos/300/300?${Random.nextInt()}"
                )
            )

            Timber.e("insertUserId: $insertUserId")

            _eventShowLoading.value = false

            // Finally reload data
            getUsers()
        }
    }

    // ----------------------------------------------------------------

    fun removeAllUsers() {
        viewModelScope.launch {
            userUseCase.removeAllUsers()

            getUsers()
        }
    }

    // ----------------------------------------------------------------

    private val _userItems: MutableLiveData<List<UserEntity>> by lazy {
        MutableLiveData<List<UserEntity>>()
    }

    val userItems: LiveData<List<UserEntity>>
        get() = _userItems

    // ----------------------------------------------------------------

    fun getUsers() = viewModelScope.launch {
        _eventShowLoading.value = true

        _userItems.value = userUseCase.getUsers()

        _eventShowLoading.value = false
    }

    fun addToFav(user: UserEntity, result: (isSuccess: Boolean) -> Unit) =
        viewModelScope.launch {
            userUseCase.updateUser(user)

            result(true)
        }

    fun removeFromFav(user: UserEntity, result: (isSuccess: Boolean) -> Unit) =
        viewModelScope.launch {
            userUseCase.updateUser(user)

            result(true)
        }
}
