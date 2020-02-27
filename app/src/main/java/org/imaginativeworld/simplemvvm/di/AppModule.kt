package org.imaginativeworld.simplemvvm.di

import android.content.Context
import dagger.Module
import dagger.Provides
import org.imaginativeworld.simplemvvm.MyApplication
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.network.ApiClient
import org.imaginativeworld.simplemvvm.network.ApiInterface
import javax.inject.Singleton

@Module
class AppModule(
    private var application: MyApplication
) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    fun provideApiInterface(): ApiInterface {
        return ApiClient.getClient()
    }

    @Provides
    fun provideAppDatabase(): AppDatabase {
        return AppDatabase(application)
    }

}