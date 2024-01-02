package com.springkotlin.repositories

import com.springkotlin.entities.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TokenRepository : JpaRepository<Token, Long> {

    fun findByToken(token: String): Token?

    @Query(value = "DELETE FROM tokens t WHERE t.user_id = :userId", nativeQuery = true)
    fun deleteAllTokensByUserId(userId: Long?)
}