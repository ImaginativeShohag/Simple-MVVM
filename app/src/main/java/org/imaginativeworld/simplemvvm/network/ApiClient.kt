/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.NullSafeJsonAdapter
import java.text.SimpleDateFormat
import java.util.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.imaginativeworld.simplemvvm.BuildConfig
import org.imaginativeworld.simplemvvm.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {
    companion object {

        @Volatile
        private var retrofit: Retrofit? = null

        @Volatile
        private var apiInterface: ApiInterface? = null

        private fun buildClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                        .build()

                    chain.proceed(request)
                }
                .build()
        }

        @Synchronized
        private fun getRetrofit(): Retrofit {
            return retrofit ?: synchronized(this) {
                val moshi = Moshi.Builder()
                    // Note: To automatically convert date string to Date object use this:
                    .add(Date::class.java, NullSafeJsonAdapter(DateJsonAdapter()))
                    .build()

                retrofit = Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(Constants.SERVER_ENDPOINT + "/")
                    .build()

                retrofit!!
            }
        }

        @Synchronized
        fun getClient(): ApiInterface {
            return apiInterface ?: synchronized(this) {
                apiInterface = getRetrofit().create(ApiInterface::class.java)

                apiInterface!!
            }
        }
    }

    class DateJsonAdapter : JsonAdapter<Date>() {
        private val dateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())

        override fun fromJson(reader: JsonReader): Date? {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull<Date>()
                return null
            }

            return try {
                val dateAsString = reader.nextString()
                dateFormat.parse(dateAsString)
            } catch (e: Exception) {
                e.printStackTrace()
                reader.nextNull<Date>()
                null
            }
        }

        override fun toJson(writer: JsonWriter, value: Date?) {
            if (value == null) {
                writer.nullValue()
            } else {
                writer.value(dateFormat.format(value))
            }
        }
    }
}
