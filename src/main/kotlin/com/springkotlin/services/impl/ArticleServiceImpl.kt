package com.springkotlin.services.impl

import com.springkotlin.entities.Article
import com.springkotlin.exceptions.APIException
import com.springkotlin.payloads.CreateArticleDto
import com.springkotlin.repositories.ArticleRepository
import com.springkotlin.services.ArticleService
import com.springkotlin.specifications.ArticleSpecificationBuilder
import com.springkotlin.utils.SortingUtils
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class ArticleServiceImpl(private val articleRepository: ArticleRepository): ArticleService {

    private val articleSpecificationBuilder = ArticleSpecificationBuilder()

    override fun getAllArticles(pageNo: Int, pageSize: Int, sortBy: String?, filter: String?): Page<Article> {

        val pageable: Pageable = if (sortBy != null) {
            PageRequest.of(pageNo, pageSize, SortingUtils.buildSort(sortBy))
        } else {
            PageRequest.of(pageNo, pageSize)
        }
        return if (filter != null) {
             articleRepository.findAll(articleSpecificationBuilder.buildFilterSpecification(filter), pageable)
        }
        else {
            articleRepository.findAll(pageable)
        }
    }

    override fun getArticleById(articleId: Long): Article? {
        return articleRepository.findById(articleId).orElse(null)
    }

    override fun createArticle(articleDto: CreateArticleDto): Article {
        val article = Article(
            title = articleDto.title,
            content = articleDto.content
        )
        return articleRepository.save(article)
    }

    override fun updateArticle(articleId: Long, updatedArticle: CreateArticleDto): Article? {

        val articleUpdate = articleRepository.findById(articleId).orElseThrow {
            NoSuchElementException("Article not found with ID: $articleId")
        }

        articleUpdate.title = updatedArticle.title
        articleUpdate.content = updatedArticle.content

        return articleRepository.save(articleUpdate)

    }

    override fun deleteArticle(articleId: Long) {
        articleRepository.findById(articleId).orElseThrow {
            NoSuchElementException("Article not found with ID: $articleId")
        }
        articleRepository.deleteById(articleId)
    }
}