package com.springkotlin.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "tokens")
data class Token(

    @Id
    @GeneratedValue
    val id: Int? = null,

    @Column(unique = true)
    val token: String,

    val revoked: Boolean,

    val expired: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    val user: User
)
