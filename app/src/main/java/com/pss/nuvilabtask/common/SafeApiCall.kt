package com.pss.nuvilabtask.common

import com.pss.nuvilabtask.model.ApiError
import com.pss.nuvilabtask.data.model.ApiResponseStatus
import com.pss.nuvilabtask.model.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

suspend inline fun <T> safeApiCallResponse(crossinline responseFunction: suspend () -> Response<T>): ApiResponseStatus<T> {
    var requestUrl = "null"
    var responseStatusCode = "null"

    return try {
        val response = withContext(Dispatchers.IO) {
            responseFunction.invoke()

        }.also {
            requestUrl = it.raw().request.url.toString()
            responseStatusCode = it.raw().code.toString()
        }

        if (response.isSuccessful) {
            ApiResponseStatus.Success(response.body()!!)
        } else {
            var code = 0
            var message = ""

            val jsonObject = response.errorBody()!!.string()
            val json = Json { ignoreUnknownKeys = true }

            val errorResponse = json.decodeFromString<ApiError>(jsonObject)

            code = errorResponse.code
            message = errorResponse.message

            ApiResponseStatus.Error(
                code = code,
                message = message,
                type = ErrorType.Http,
            )
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            e.printStackTrace()
            ApiResponseStatus.Error(
                code = 0,
                message = "",
                type = when (e) {
                    is HttpException -> ErrorType.Http
                    is SocketTimeoutException -> ErrorType.Timeout
                    is IOException -> ErrorType.Network
                    else -> ErrorType.Unknown
                }
            )
        }
    }
}