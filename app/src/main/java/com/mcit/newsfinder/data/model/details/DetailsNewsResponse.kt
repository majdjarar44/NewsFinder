package com.mcit.newsfinder.data.model.details

data class DetailsNewsResponse(
    val nextPage: Int,
    val results: List<Result>,
    val status: String,
    val totalResults: Int
)