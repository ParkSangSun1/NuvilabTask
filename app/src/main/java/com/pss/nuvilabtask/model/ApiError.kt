package com.pss.nuvilabtask.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val status: Int,
    val code: Int,
    val message: String,
//    val detail: String
)
