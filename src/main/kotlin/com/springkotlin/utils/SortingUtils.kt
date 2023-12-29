package com.springkotlin.utils

import org.springframework.data.domain.Sort

object SortingUtils {

    fun buildSort(sort: String): Sort {
        val sortValues = sort.split(",")
        return if (sortValues.isNotEmpty()) {

            val orders = Array<Sort.Order>(sortValues.size / 2) {
                Sort.Order(
                    Sort.Direction.fromString(sortValues[1]),
                    AppConstant.DEFAULT_SORT_BY
                )
            }

            for (i in sortValues.indices step 2) {
                val field = sortValues[i]
                val direction = sortValues[i + 1]
                orders[i / 2] = Sort.Order(Sort.Direction.fromString(direction), field)
            }

            Sort.by(*orders)
        } else {
            Sort.unsorted()
        }
    }
}