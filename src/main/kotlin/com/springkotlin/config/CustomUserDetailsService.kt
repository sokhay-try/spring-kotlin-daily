package com.springkotlin.config

import com.springkotlin.entities.Role
import com.springkotlin.repositories.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {

        val user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow {
                UsernameNotFoundException("User not found with username or email: $usernameOrEmail")
            }

        val roles =  userRepository.findRolesByUserId(user.id)

        val authorities = roles
            .stream()
            .map<SimpleGrantedAuthority> { role: Role ->
                SimpleGrantedAuthority(
                    role.name
                )
            }
            .collect(Collectors.toSet<GrantedAuthority>())

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            authorities
        )


    }

}