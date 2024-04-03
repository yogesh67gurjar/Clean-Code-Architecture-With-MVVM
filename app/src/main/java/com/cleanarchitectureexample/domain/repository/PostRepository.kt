package com.cleanarchitectureexample.domain.repository

import com.cleanarchitectureexample.domain.model.PostResponse

interface PostRepository {
    // fetching data from remote
    suspend fun getPosts(id: Int): PostResponse

    // fetching data from local
    suspend fun getSavedPosts(): List<PostResponse>

    // saving data to local
    suspend fun savePost(post: PostResponse)
}