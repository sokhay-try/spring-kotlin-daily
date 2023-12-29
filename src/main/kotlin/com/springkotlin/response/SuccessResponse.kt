package com.springkotlin.response

data class SuccessResponse(
    var status: String = "success",
    var data: Any?
)
