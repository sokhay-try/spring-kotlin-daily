package com.springkotlin.response

data class ErrorResponse(
    var status: String = "fail",
    var message: String? = null,
    var errors: List<Map<String, String>> = ArrayList()
)