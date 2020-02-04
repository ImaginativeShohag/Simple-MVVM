package org.imaginativeworld.simplemvvm.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.imaginativeworld.simplemvvm.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {

        @Volatile
        private var retrofit: Retrofit? = null

        @Volatile
        private var apiInterface: ApiInterface? = null

        private fun buildClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()

                    chain.proceed(request)
                }
                .build()
        }

        @Synchronized
        private fun getRetrofit(): Retrofit {
            return retrofit ?: synchronized(this) {

                val gson = GsonBuilder()
                    // Note: To automatically convert date string to Date object use this:
                    // .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create()

                retrofit ?: Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(Constants.SERVER_ENDPOINT + "/")
                    .build()
            }
        }

        @Synchronized
        fun getClient(): ApiInterface {

            return apiInterface ?: synchronized(this) {

                getRetrofit().create(ApiInterface::class.java)

            }

        }

    }
}