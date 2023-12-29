package com.springkotlin.response

data class Pagination(
    var currentPage: Int,
    var pageSize: Int,
    var totalElements: Long,
    var totalPages: Int,
    var last: Boolean
)
