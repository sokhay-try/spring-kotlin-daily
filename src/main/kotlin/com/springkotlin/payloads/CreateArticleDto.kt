package com.springkotlin.payloads

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateArticleDto(

    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    val title: String,

    @field:NotBlank(message = "Content cannot be blank")
    @field:Size(min = 10, message = "Content must be at least 10 characters")
    val content: String

)
