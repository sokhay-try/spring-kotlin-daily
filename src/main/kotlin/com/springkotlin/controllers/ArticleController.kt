package com.springkotlin.controllers

import com.springkotlin.entities.Article
import com.springkotlin.payloads.CreateArticleDto
import com.springkotlin.response.ListResponse
import com.springkotlin.response.Pagination
import com.springkotlin.response.SuccessResponse
import com.springkotlin.services.ArticleService
import com.springkotlin.utils.AppConstant
import com.springkotlin.utils.PaginationUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppConstant.BASE_URL + "articles")
class ArticleController(private val articleService: ArticleService) {

    @GetMapping
    fun getAllUsers(
        @RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) pageNo: Int,
        @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) pageSize: Int,
        @RequestParam(value = "sort", required = false) sort: String?,
        @RequestParam(value = "q", required = false) filter: String?
    ): ResponseEntity<SuccessResponse> {

        val articles = articleService.getAllArticles(pageNo, pageSize, sort, filter)
        val contents = articles.content

        // set up pagination
        val pagination: Pagination = PaginationUtil.build(articles)

        // set up list response
        val listResponse: ListResponse = ListResponse(content = contents, pagination = pagination)

        val successResponse = SuccessResponse(data = listResponse)

        return ResponseEntity.ok(successResponse)
    }

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: Long): ResponseEntity<SuccessResponse> {
        val article = articleService.getArticleById(id)
        val successResponse = SuccessResponse(data = article)
        return ResponseEntity.ok(successResponse)
    }

    @PostMapping
    fun createArticle(@Valid @RequestBody article: CreateArticleDto): ResponseEntity<Article>  {
        return ResponseEntity.ok(articleService.createArticle(article))
    }

    @PutMapping("/{id}")
    fun updateArticle(@PathVariable id: Long, @Valid @RequestBody updatedArticle: CreateArticleDto): ResponseEntity<Article> {
        return ResponseEntity.ok(articleService.updateArticle(id, updatedArticle))
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable id: Long): ResponseEntity<SuccessResponse> {
        articleService.deleteArticle(id)
        val successResponse = SuccessResponse(data = "Article deleted successfully")
        return ResponseEntity.ok(successResponse)
    }
}