package com.pss.nuvilabtask.data.model

import com.pss.nuvilabtask.model.ErrorType

sealed class ApiResponseStatus<out T>() {
    data class Success<T>(val data: T) : ApiResponseStatus<T>()
    data class Error(
        val code: Int,
        val message: String,
        val type: ErrorType,
    ) : ApiResponseStatus<Nothing>()
}

fun <T, DT> ApiResponseStatus<DT>.toUiStatus(model: T?): ApiResponseStatus<T> {
    return try {
        when(this){
            is ApiResponseStatus.Success -> {
                ApiResponseStatus.Success(model!!)
            }
            is ApiResponseStatus.Error -> ApiResponseStatus.Error(
                code = this.code,
                message = this.message,
                type = this.type
            )
        }
    }catch (e : Exception){
        ApiResponseStatus.Error(
            code = 0,
            message = "",
            type = ErrorType.Unknown
        )
    }
}