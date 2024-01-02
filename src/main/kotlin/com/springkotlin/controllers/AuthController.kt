package com.springkotlin.controllers

import com.springkotlin.entities.User
import com.springkotlin.payloads.LoginDto
import com.springkotlin.payloads.RegisterDto
import com.springkotlin.response.LoginResponse
import com.springkotlin.response.SuccessResponse
import com.springkotlin.services.AuthService
import com.springkotlin.utils.AppConstant
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppConstant.BASE_URL + "auth")
class AuthController (
    private val authService: AuthService
        ) {

    @PostMapping(value = ["/login"])
    fun login(@Valid @RequestBody loginDto: LoginDto): ResponseEntity<SuccessResponse> {

        val loginResponse: LoginResponse = authService.login(loginDto)
        val successResponse = SuccessResponse(
            data = loginResponse
        )

        return ResponseEntity.ok(successResponse)
    }

    @PostMapping(value = ["/register"])
    fun createUser(@Valid @RequestBody registerDto: RegisterDto): ResponseEntity<User> {
        val registerUser = authService.register(registerDto)

        return ResponseEntity<User>(registerUser, HttpStatus.CREATED)
    }

}