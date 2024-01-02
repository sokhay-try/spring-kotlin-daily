package com.springkotlin.services.impl

import com.springkotlin.entities.Token
import com.springkotlin.repositories.TokenRepository
import com.springkotlin.services.TokenService
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl(
    private val tokenRepository: TokenRepository
) : TokenService {
    override fun findByToken(token: String): Token? {
        return tokenRepository.findByToken(token)
    }

    override fun deleteById(id: Long) {
        tokenRepository.deleteById(id)
    }
}