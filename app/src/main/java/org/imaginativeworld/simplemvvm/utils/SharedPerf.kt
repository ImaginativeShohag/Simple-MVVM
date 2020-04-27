package org.imaginativeworld.simplemvvm.utils

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import org.imaginativeworld.simplemvvm.BuildConfig
import org.imaginativeworld.simplemvvm.models.UserEntity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPerf @Inject constructor(
    context: Context
) {

    companion object {
        private const val PREF_TOKEN = "token"
        private const val PREF_USER = "user"
    }


    private val context: Context = context.applicationContext

    @Volatile
    private var sharedPerf: SharedPreferences? = null

    @Volatile
    private var user: UserEntity? = null

    private fun getSharedPerf(): SharedPreferences {
        return sharedPerf ?: synchronized(this) {
            context.getSharedPreferences(
                "${BuildConfig.APPLICATION_ID}.main",
                Context.MODE_PRIVATE
            )
        }
    }

    fun reset() {
        getSharedPerf().edit().clear().apply()

        user = null
    }

    // ----------------------------------------------------------------

    fun setToken(token: String) {
        getSharedPerf()
            .edit()
            .apply {
                putString(PREF_TOKEN, token)
                apply()
            }
    }

    fun getToken(): String? {
        return getSharedPerf().getString(PREF_TOKEN, null)
    }

    // ----------------------------------------------------------------

    fun setUser(user: UserEntity) {
        getSharedPerf()
            .edit()
            .apply {
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter(UserEntity::class.java)

                putString(PREF_USER, jsonAdapter.toJson(user))
                apply()
            }
    }

    fun getUser(): UserEntity? {
        return user ?: synchronized(this) {
            getSharedPerf()
                .let {
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter = moshi.adapter(UserEntity::class.java)

                    val userJson = it.getString(PREF_USER, null)

                    user = if (userJson == null) {
                        null
                    } else {
                        jsonAdapter.fromJson(userJson)
                    }

                    Timber.d("user: $user")

                    user
                }
        }
    }

    // ----------------------------------------------------------------

    fun isUserLoggedIn(): Boolean {
        return getUser() != null
    }


}