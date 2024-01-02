package com.springkotlin.services.impl

import com.springkotlin.entities.Role
import com.springkotlin.entities.Token
import com.springkotlin.entities.User
import com.springkotlin.repositories.RoleRepository
import com.springkotlin.repositories.TokenRepository
import com.springkotlin.repositories.UserRepository
import com.springkotlin.services.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tokenRepository: TokenRepository

) : UserService {

    private val logger: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    override fun getRolesByUserId(userId: Long): List<Role> {
        return userRepository.findRolesByUserId(userId)
    }

    @Transactional
    override fun addRolesToUser(userId: Long, roleIds: Set<Long>): User? {
        val user = userRepository.findById(userId).orElse(null)

        if (user != null && !roleIds.isNullOrEmpty()) {
            // Fetch the existing roles of the user
            val existingRoles = user.id?.let { getRolesByUserId(it) }
            val existingRoleIds = existingRoles?.map { it.id } ?: emptySet()

            // Filter out the roles that the user already has
            val newRoles = roleRepository.findAllById(roleIds)
                .filter { role -> role.id !in existingRoleIds }
                .toSet()

            user.roles.addAll(newRoles)
            userRepository.save(user)
        }

        return user
    }

    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        val jwt: String = authHeader.substring(7)
        try {
            val storedToken: Token? = this.tokenRepository.findByToken(jwt)

            storedToken?.let { token ->
                val userId: Long? = token.user.id
                tokenRepository.deleteAllTokensByUserId(userId)
                SecurityContextHolder.clearContext()
            }
        }
        catch (ex: Exception) {
            logger.error("Exception = " + ex.message)
        }
    }


}