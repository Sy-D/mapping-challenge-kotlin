package com.mhp.coding.challenges.mapping.controllers

import com.mhp.coding.challenges.mapping.mappers.ArticleMappingException
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import com.mhp.coding.challenges.mapping.services.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/article")
class ArticleController(
    private val articleService: ArticleService
) {
    @GetMapping
    fun list(): List<ArticleDto> {
        return try {
            articleService.list()
        } catch(e: ArticleMappingException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message);
        }
    }

    @GetMapping("/{id}")
    fun details(@PathVariable id: Long): ArticleDto {
        try {
            return articleService.articleForId(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } catch (e: ArticleMappingException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PostMapping
    fun create(@RequestBody articleDto: ArticleDto): ArticleDto = articleService.create(articleDto)
}
