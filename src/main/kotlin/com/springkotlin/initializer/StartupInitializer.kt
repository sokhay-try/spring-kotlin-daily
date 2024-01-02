package com.springkotlin.initializer

import com.springkotlin.entities.Role
import com.springkotlin.repositories.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class StartupInitializer(private val roleRepository: RoleRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val roles = roleRepository.findAll()

        if (roles == null || roles.isEmpty()) {
            val roleAdmin = Role(name = "ROLE_ADMIN")
            val roleUser = Role( name = "ROLE_USER")

            roleRepository.save(roleAdmin)
            roleRepository.save(roleUser)
        }
    }
}