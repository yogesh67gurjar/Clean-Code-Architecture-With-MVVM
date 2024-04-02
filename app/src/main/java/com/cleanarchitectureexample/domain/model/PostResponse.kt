package com.cleanarchitectureexample.domain.model

data class PostResponse(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)