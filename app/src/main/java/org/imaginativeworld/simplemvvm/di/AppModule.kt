package org.imaginativeworld.simplemvvm.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.network.ApiClient
import org.imaginativeworld.simplemvvm.network.ApiInterface

@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Provides
    fun provideApiInterface(): ApiInterface {
        return ApiClient.getClient()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase(context)
    }
}