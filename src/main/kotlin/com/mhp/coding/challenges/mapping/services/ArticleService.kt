package com.mhp.coding.challenges.mapping.services

import com.mhp.coding.challenges.mapping.repositories.ArticleRepository
import com.mhp.coding.challenges.mapping.mappers.ArticleMapper
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleService(
    private val mapper: ArticleMapper,
) {
    fun list(): List<ArticleDto> {
        val articles = ArticleRepository.all()

        var result = mutableListOf<ArticleDto>()
        for (article in articles) {
            var articleDto = mapper.map(article)
            Collections.sort(articleDto.blocks.toList())
            result.add(articleDto)
        }

        return result
    }

    fun articleForId(id: Long): ArticleDto? {
        val article = ArticleRepository.findBy(id)

        if (article == null) {
            return null
        }

        var result = mapper.map(article)
        Collections.sort(result.blocks.toList())
        return result
    }

    fun create(articleDto: ArticleDto): ArticleDto {
        val article = mapper.map(articleDto)
        ArticleRepository.create(article)
        return mapper.map(article)
    }
}
