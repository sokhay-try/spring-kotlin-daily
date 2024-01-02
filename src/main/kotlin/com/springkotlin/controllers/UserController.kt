package com.springkotlin.controllers

import com.springkotlin.payloads.AddRolesDto
import com.springkotlin.response.SuccessResponse
import com.springkotlin.services.UserService
import com.springkotlin.utils.AppConstant
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.BASE_URL + "users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<*> {
        val user = userService.getUserById(id)

        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build<Any>()
        }
    }

    @GetMapping("/{id}/roles")
    fun getRolesByUserId(@PathVariable id: Long): ResponseEntity<*> {
        val roles = userService.getRolesByUserId(id)

        return if (roles != null) {
            ResponseEntity.ok(roles)
        } else {
            ResponseEntity.notFound().build<Any>()
        }
    }

    @PostMapping("/{id}/addRoles")
    fun addRolesToUser(
        @PathVariable id: Long,
        @RequestBody addRolesDto: AddRolesDto
    ): ResponseEntity<*> {

        val updatedUser = userService.addRolesToUser(id, addRolesDto.roleIds)

        return if (updatedUser != null) {
            ResponseEntity.ok(updatedUser)
        } else {
            ResponseEntity.notFound().build<Any>()
        }
    }

    @PostMapping(value = ["/logout"])
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ): ResponseEntity<*> {

        userService.logout(request, response, authentication)
        val successResponse = SuccessResponse(data = null)
        return ResponseEntity.ok<Any>(successResponse)
    }
}