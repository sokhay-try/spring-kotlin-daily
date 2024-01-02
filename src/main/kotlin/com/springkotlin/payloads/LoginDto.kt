package com.springkotlin.payloads

import jakarta.validation.constraints.*

data class LoginDto(
    @field:NotNull(message = "Username is required")
    @field:NotBlank(message = "Username must not be blank")
    @field:NotEmpty(message = "Username must not be empty")
    val username: String,

    @field:NotNull(message = "Password is required")
    @field:NotBlank(message = "Password must not be blank")
    @field:NotEmpty(message = "Password must not be empty")
    val password: String
)

