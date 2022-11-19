package com.mcit.newsfinder.data.model

data class MixedNewsDataModel(
    val author: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val category: List<String>,
    val country: List<String>,
)