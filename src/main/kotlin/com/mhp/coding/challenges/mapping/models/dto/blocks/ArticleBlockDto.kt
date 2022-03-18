package com.mhp.coding.challenges.mapping.models.dto.blocks

interface ArticleBlockDto : Comparable<ArticleBlockDto> {
    val sortIndex: Int

    override operator fun compareTo(other: ArticleBlockDto): Int {
        return sortIndex - other.sortIndex
    }
}
