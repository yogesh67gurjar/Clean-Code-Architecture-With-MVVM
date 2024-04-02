package com.cleanarchitectureexample.domain.repository

import com.cleanarchitectureexample.domain.model.PostResponse

interface PostRepository {
    suspend fun getPosts(id: Int): PostResponse
}