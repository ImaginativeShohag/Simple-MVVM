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

package org.imaginativeworld.simplemvvm.usecase

import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import org.imaginativeworld.simplemvvm.repositories.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend fun addUser(userModel: DemoUserEntity) {
        repository.saveUserInDB(userModel)
    }

    suspend fun removeAllUsers() {
        repository.removeAllUsersFromDB()
    }

    suspend fun getUsers(): List<DemoUserEntity> {
        return repository.getUsersFromDB()
    }

    suspend fun updateUser(user: DemoUserEntity) {
        return repository.updateUserToDB(user)
    }
}
