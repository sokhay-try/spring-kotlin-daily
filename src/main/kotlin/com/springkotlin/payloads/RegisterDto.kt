package com.springkotlin.payloads

import jakarta.validation.constraints.*

data class RegisterDto(

    @field:NotNull(message = "Username is required")
    @field:NotBlank(message = "Username must not be blank")
    @field:NotEmpty(message = "Username must not be empty")
    @field:Size(
        message = "Username must be between 5 and 20 characters",
        min = 5,
        max = 20
    )
    val username: String,

    @field:NotNull(message = "First name is required")
    @field:NotBlank(message = "First name must not be blank")
    @field:NotEmpty(message = "First name must not be empty")
    val firstName: String,

    @field:NotNull(message = "Last name is required")
    @field:NotBlank(message = "Last name must not be blank")
    @field:NotEmpty(message = "Last name must not be empty")
    val lastName: String,

    @field:NotNull(message = "Email is required")
    @field:NotBlank(message = "Email must not be blank")
    @field:NotEmpty(message = "Email must not be empty")
    @field:Email(message = "Email must be a well-formed email address")
    val email: String,

    @field:NotNull(message = "Phone number is required")
    @field:NotBlank(message = "Phone number must not be blank")
    @field:NotEmpty(message = "Phone number must not be empty")
    @field:Pattern(
        message = "Phone number must be contain 9 or 10 digits",
        regexp = "^\\d{9,10}$"
    )
    val phoneNumber: String,

    @field:NotNull(message = "Password is required")
    @field:NotBlank(message = "Password must not be blank")
    @field:NotEmpty(message = "Password must not be empty")
    @field:Size(min = 5, max = 20)
    @field:Pattern(
        message = "Password contains at least one uppercase letter, one lowercase letter, one number, one symbol and 8 characters",
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
    )
    val password: String,

    @field:NotNull(message = "Role is required")
    @field:NotBlank(message = "Role must not be blank")
    @field:NotEmpty(message = "Role must not be empty")
    val role: String
)
