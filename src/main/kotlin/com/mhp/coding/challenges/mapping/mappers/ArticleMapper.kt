package com.mhp.coding.challenges.mapping.mappers

import com.mhp.coding.challenges.mapping.models.db.Article
import com.mhp.coding.challenges.mapping.models.db.Image
import com.mhp.coding.challenges.mapping.models.db.blocks.*
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import com.mhp.coding.challenges.mapping.models.dto.ImageDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.ArticleBlockDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.GalleryBlockDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class ArticleMapper {

    /**
     * @description Maps the specified article to a client facing dto.
     * @param article - The Article
     * @return The dto
     */
    fun map(article: Article): ArticleDto {
        return ArticleDto(article.id, article.title, article.description ?: "", article.author ?: "",
            article.blocks.map { block -> map(block) })
    }

    // Not part of the challenge / Nicht Teil dieser Challenge.
    fun map(articleDto: ArticleDto?): Article = Article(
        title = "An Article",
        blocks = emptySet(),
        id = 1,
        lastModified = Date()
    )

    // Not part of the challenge
    private fun map(articleBlock: ArticleBlockDto): ArticleBlock = ArticleBlock(articleBlock.sortIndex)

    private fun map(articleBlock: ArticleBlock): ArticleBlockDto {
        if (articleBlock is ImageBlock && articleBlock.image == null) {
            throw NullPointerException()
        }

        var blockDto: ArticleBlockDto = when(articleBlock) {
            is GalleryBlock -> GalleryBlockDto(map(articleBlock.images.filterNotNull()), articleBlock.sortIndex)
            is ImageBlock -> com.mhp.coding.challenges.mapping.models.dto.blocks.ImageBlock(
                map(articleBlock.image!!), articleBlock.sortIndex)
            is TextBlock -> com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlock(articleBlock.text,
                articleBlock.sortIndex)
            is VideoBlock -> com.mhp.coding.challenges.mapping.models.dto.blocks.VideoBlock(articleBlock.url,
                articleBlock.type, articleBlock.sortIndex)
            else -> throw ArticleMappingException("Block type does not exist")
        }

        return blockDto
    }

    private fun map(image: Image): ImageDto {
            return ImageDto(image.id, image.url, image.imageSize)
    }

    private fun map(images: Iterable<Image>): List<ImageDto> {
        return images.map{image -> map(image)}
    }
}
