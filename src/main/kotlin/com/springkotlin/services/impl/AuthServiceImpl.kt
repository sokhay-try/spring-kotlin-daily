package com.springkotlin.services.impl

import com.springkotlin.config.JwtTokenService
import com.springkotlin.entities.Role
import com.springkotlin.entities.Token
import com.springkotlin.entities.User
import com.springkotlin.exceptions.APIException
import com.springkotlin.payloads.LoginDto
import com.springkotlin.payloads.RegisterDto
import com.springkotlin.repositories.RoleRepository
import com.springkotlin.repositories.TokenRepository
import com.springkotlin.repositories.UserRepository
import com.springkotlin.response.LoginResponse
import com.springkotlin.services.AuthService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authManager: AuthenticationManager,
    private val jwtTokenService: JwtTokenService,

) : AuthService {

    override fun login(loginDto: LoginDto): LoginResponse {
         var authentication: Authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.username,
                loginDto.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication

        val user = userRepository.findByUsernameOrEmail(loginDto.username, loginDto.username).orElseThrow()

        val accessToken = jwtTokenService.generate(authentication = authentication)

        val loginResponse = LoginResponse(
            user = user,
            accessToken = accessToken
        )

        saveUserToken(user, accessToken)

        return loginResponse
    }


    override fun register(registerDto: RegisterDto): User {
        // Check if username already exists in the database
        if (userRepository.existsByUsername(registerDto.username)) {
            throw APIException(HttpStatus.BAD_REQUEST, "Username is already exists")
        }

        // Check if email already exists in the database
        if (userRepository.existsByEmail(registerDto.email)) {
            throw APIException(HttpStatus.BAD_REQUEST, "Email is already exists")
        }

        val roles = mutableSetOf<Role>()
        val userRole = roleRepository.findByName("ROLE_" + registerDto.role).get()
        roles.add(userRole)

        val user = User(
            username = registerDto.username,
            firstName = registerDto.firstName,
            lastName = registerDto.lastName,
            phoneNumber = registerDto.phoneNumber,
            email = registerDto.email,
            password = passwordEncoder.encode(registerDto.password),
            roles = roles
        )

        userRepository.save(user)

        return user
    }

    private fun saveUserToken(user: User, jwtToken: String) {
        val token = Token(
            token = jwtToken,
            user = user,
            expired = false,
            revoked = false
        )
        tokenRepository.save(token)
    }
}