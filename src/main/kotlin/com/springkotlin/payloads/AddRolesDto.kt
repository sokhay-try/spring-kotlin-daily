package com.springkotlin.payloads

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class AddRolesDto(
    @field:NotNull(message = "Role id is required")
    @field:NotBlank(message = "Role id must not be blank")
    @field:NotEmpty(message = "Role id must not be empty")
    val roleIds: Set<Long>,
)
