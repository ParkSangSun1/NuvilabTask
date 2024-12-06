package com.pss.nuvilabtask.model

sealed class ErrorType {
    object Http : ErrorType()
    object Network : ErrorType() // IO
    object Timeout : ErrorType()// Socket
    object Unknown: ErrorType() //Anything else
}