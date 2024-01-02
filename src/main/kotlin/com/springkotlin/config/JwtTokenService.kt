package com.springkotlin.config

import com.springkotlin.entities.Token
import com.springkotlin.services.TokenService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtTokenService(
    private val tokenService: TokenService
) {

    @Value("\${app.jwt-secret}")
    private lateinit var secretKeyString: String
    private lateinit var secretKey: SecretKey

    @Value("\${app-jwt-expiration-milliseconds}")
    private val jwtExpirationDate: Long = 0

    @PostConstruct
    private fun init() {
        // Convert the String secretKey to a SecretKey
        secretKey = Keys.hmacShaKeyFor(secretKeyString.toByteArray(StandardCharsets.UTF_8))
    }

    fun generate(
        authentication: Authentication,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String {
        val currentDate = Date()
        var expireDate: Date = Date(currentDate.time + jwtExpirationDate)

        return Jwts.builder()
            .claims()
            .subject(authentication.name)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expireDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()
    }

    fun isValid(token: String, userDetails: UserDetails): Boolean {

        // check token is expired and revoke

        // check token is expired and revoke
        val checkToken: Token = this.tokenService.findByToken(token) ?: return false

        val email = extractEmail(token)

        return userDetails.username == email && !isExpired(token)
    }

    fun extractEmail(token: String): String? =
        getAllClaims(token)
            .subject

    fun isExpired(token: String): Boolean =
        getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }
}