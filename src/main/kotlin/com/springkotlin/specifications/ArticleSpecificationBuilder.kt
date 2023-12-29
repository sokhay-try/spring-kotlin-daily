package com.springkotlin.specifications

import com.springkotlin.entities.Article
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

class ArticleSpecificationBuilder {

    fun buildFilterSpecification(filter: String): Specification<Article> {
        return Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            // Customize this part based on your entity's fields
            // Assuming "title" is a String property
            predicates.add(
                criteriaBuilder.like(root.get<String>("title"), "%$filter%")
            )
            predicates.add(
                criteriaBuilder.like(root.get<String>("content"), "%$filter%")
            )

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

}