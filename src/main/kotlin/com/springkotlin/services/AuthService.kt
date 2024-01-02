package com.springkotlin.services

import com.springkotlin.entities.User
import com.springkotlin.payloads.LoginDto
import com.springkotlin.payloads.RegisterDto
import com.springkotlin.response.LoginResponse

interface AuthService {
    fun login(loginDto: LoginDto): LoginResponse
    fun register(registerDto: RegisterDto): User
}
