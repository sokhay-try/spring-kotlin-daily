package com.springkotlin.services

import com.springkotlin.entities.Token
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TokenService {
    fun findByToken(token: String): Token?
    fun deleteById(id: Long)
}