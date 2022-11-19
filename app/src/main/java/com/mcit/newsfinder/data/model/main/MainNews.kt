package com.mcit.newsfinder.data.model.main

data class MainNews(
    val articles: ArrayList<Article>,
    val status: String,
    val totalResults: Int
)