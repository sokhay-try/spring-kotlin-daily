package com.springkotlin.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.springkotlin.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val errorResponse = ErrorResponse(
            message = authException?.message
        )

        if (response != null) {
            response.status = HttpStatus.FORBIDDEN.value()
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(ObjectMapper().writeValueAsString(errorResponse))
        }

    }
}