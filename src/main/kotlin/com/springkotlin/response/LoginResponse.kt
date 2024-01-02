package com.springkotlin.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.springkotlin.entities.User

data class LoginResponse(
    val user: User,
    @JsonProperty("accessToken")
    val accessToken: String
)
