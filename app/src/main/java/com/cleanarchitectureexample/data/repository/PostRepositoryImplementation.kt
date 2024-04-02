package com.cleanarchitectureexample.data.repository

import com.cleanarchitectureexample.data.remote.ApiService
import com.cleanarchitectureexample.domain.repository.PostRepository
import com.cleanarchitectureexample.domain.model.PostResponse
import javax.inject.Inject

class PostRepositoryImplementation @Inject constructor(private val apiService: ApiService) :
    PostRepository {
    override suspend fun getPosts(id: Int): PostResponse = apiService.getPost(id)
}