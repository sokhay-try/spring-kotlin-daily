package com.springkotlin.services

import com.springkotlin.entities.Role
import com.springkotlin.entities.User
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication

interface UserService {
    fun getUserById(id: Long): User?
    fun getRolesByUserId(userId: Long): List<Role>?
    fun addRolesToUser(userId: Long, roleIds: Set<Long>): User?
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    )
}