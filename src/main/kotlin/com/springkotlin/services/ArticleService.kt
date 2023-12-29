package com.springkotlin.services

import com.springkotlin.entities.Article
import com.springkotlin.payloads.CreateArticleDto
import org.springframework.data.domain.Page

interface ArticleService {

    fun getAllArticles(pageNo: Int, pageSize:Int, sort: String?, filter: String?): Page<Article>
    fun getArticleById(articleId: Long): Article?
    fun createArticle(article: CreateArticleDto): Article
    fun updateArticle(articleId: Long, updatedArticle: CreateArticleDto): Article?
    fun deleteArticle(articleId: Long)

}