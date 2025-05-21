package com.app.webbyskytodolist.utils

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

abstract class BaseApiResponse {

    suspend fun<T> safeApiCall(api: suspend () -> Response<T>): NetworkResult<T>{
        try {
            val response = api()
            val body = response.body()
            if(response.isSuccessful){
                body?.let {
                    return NetworkResult.Success(body)
                } ?: return NetworkResult.Error(null, "Unknown error")
            }else{
                val errorBody = response.errorBody()?.string()
                val message = if (!errorBody.isNullOrEmpty()) {
                    try {
                        val jsonObject = JSONObject(errorBody)
                        jsonObject.getString("message")
                    } catch (e: JSONException) {
                        "Unknown error"
                    }
                } else {
                    "Empty error body"
                }

                val error: String = message
                return NetworkResult.Error(null, error)
            }
        }catch (e: Exception){
            return NetworkResult.Error(null, "Unknown error")
        }
    }
}