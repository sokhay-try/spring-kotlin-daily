package com.springkotlin.repositories

import com.springkotlin.entities.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ArticleRepository : JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

}