package com.springkotlin.repositories

import com.springkotlin.entities.Role
import com.springkotlin.entities.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserRepository : JpaRepository<User, Long>{

    fun findByEmail(email: String): Optional<User>

    fun findByUsernameOrEmail(username: String, email: String): Optional<User>

    fun findByUsername(username: String): Optional<User>

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun findByResetPasswordToken(token: String): Optional<User>

    @Query(value = "DELETE FROM users_roles us WHERE us.user_id = :userId", nativeQuery = true)
    fun deleteAllRolesByUserId(userId: Long)

    @Query("SELECT u.roles FROM User u WHERE u.id = :userId")
    fun findRolesByUserId(@Param("userId") userId: Long?): List<Role>
}