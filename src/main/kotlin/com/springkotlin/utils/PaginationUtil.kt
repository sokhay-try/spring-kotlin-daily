package com.springkotlin.utils

import com.springkotlin.response.Pagination
import org.springframework.data.domain.Page

class PaginationUtil {
    companion object {
        fun build(obj: Page<*>): Pagination {
            // set up pagination
            return Pagination(
                currentPage = obj.number,
                pageSize = obj.size,
                totalElements = obj.totalElements,
                totalPages = obj.totalPages,
                last = obj.isLast
            )
        }
    }
}