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

package org.imaginativeworld.simplemvvm.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.network.ApiClient
import org.imaginativeworld.simplemvvm.network.api.PostApiInterface
import org.imaginativeworld.simplemvvm.network.api.TodoApiInterface
import org.imaginativeworld.simplemvvm.network.api.UserApiInterface
import org.imaginativeworld.simplemvvm.utils.extensions.MoshiUtil
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase(context)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return MoshiUtil.getMoshi()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return ApiClient.getRetrofit(moshi)
    }

    @Singleton
    @Provides
    fun provideUserApiInterface(retrofit: Retrofit): UserApiInterface {
        return retrofit.create(UserApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideTodoApiInterface(retrofit: Retrofit): TodoApiInterface {
        return retrofit.create(TodoApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun providePostApiInterface(retrofit: Retrofit): PostApiInterface {
        return retrofit.create(PostApiInterface::class.java)
    }
}
