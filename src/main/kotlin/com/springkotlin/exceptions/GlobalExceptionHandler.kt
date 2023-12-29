package com.springkotlin.exceptions

import com.springkotlin.response.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message,
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message,
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(APIException::class)
    fun handleAPIException(ex: APIException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message,
        )
        return ResponseEntity(errorResponse, ex.status)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = getAllErrors(ex.bindingResult.allErrors)
        val errorResponse = ErrorResponse(
            message = "Validation errors in your request",
            errors = errors
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // When wrong username or password login

    //    @ExceptionHandler(BadCredentialsException::class)
//    fun handleBadCredentialsException(ex: BadCredentialsException, request: WebRequest): ResponseEntity<ErrorResponse> {
//        val errorResponse = ErrorResponse(
//            message = "Incorrect username or password",
//        )
//        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
//    }

    private fun getAllErrors(errors: List<ObjectError>): List<Map<String, String>> {
        val allErrors = errors.mapNotNull { error ->
            if (error is FieldError) {
                error.field to error.defaultMessage.orEmpty()
            } else {
                null
            }
        }.toMap()

        return listOf(allErrors)
    }



}
