package org.imaginativeworld.simplemvvm.network

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        try {

            val response = call.invoke()

            // && response.code() == HttpURLConnection.HTTP_OK
            if (response.isSuccessful) {

                return response.body()!!

            } else {

                val error = response.errorBody()?.string()

                val message = StringBuilder()
                error?.let {

                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                    }

                    message.append("\n")
                }

                message.append("Error Code: ${response.code()}")

                Log.e("SafeApiRequest", "ApiException: $message")

                throw ApiException(message.toString())
            }

        } catch (e: Exception) {

            e.printStackTrace()

            throw ApiException(e.message ?: "Unknown Error!")

        }
    }

}