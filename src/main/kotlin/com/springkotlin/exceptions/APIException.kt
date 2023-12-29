package com.springkotlin.exceptions

import org.springframework.http.HttpStatus

class APIException(status: HttpStatus, message: String) : RuntimeException(message) {

    val status: HttpStatus = status
    override val message: String = message
}
