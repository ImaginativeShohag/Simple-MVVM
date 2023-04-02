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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import org.imaginativeworld.simplemvvm.models.awesometodos.User
import org.imaginativeworld.simplemvvm.network.SafeApiRequest
import org.imaginativeworld.simplemvvm.network.api.UserApiInterface
import javax.inject.Inject

class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: UserApiInterface,
    private val db: AppDatabase,
) {
    suspend fun getUser(userId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getUser(userId)
        }
    }

    suspend fun signIn(user: User) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.createUser(user)
        }
    }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        db.todoDao().removeAll()
    }

    // ----------------------------------------------------------------
    // Database
    // ----------------------------------------------------------------

    suspend fun saveUserInDB(
        userModel: DemoUserEntity,
    ): Long {
        return withContext(Dispatchers.IO) {
            db.userDao().insertUser(userModel)
        }
    }

    suspend fun removeAllUsersFromDB() {
        return withContext(Dispatchers.IO) {
            db.userDao().removeAllUsers()
        }
    }

    suspend fun getUsersFromDB(): List<DemoUserEntity> {
        return withContext(Dispatchers.IO) {
            db.userDao().getUsers()
        }
    }

    suspend fun updateUserToDB(user: DemoUserEntity) {
        return withContext(Dispatchers.IO) {
            db.userDao().updateUser(user)
        }
    }
}