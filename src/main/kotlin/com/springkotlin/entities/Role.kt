package com.springkotlin.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    val users: MutableSet<User> = mutableSetOf()
)
