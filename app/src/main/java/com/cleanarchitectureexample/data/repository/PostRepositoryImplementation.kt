package com.cleanarchitectureexample.data.repository

import com.cleanarchitectureexample.data.remote.ApiService
import com.cleanarchitectureexample.database.AppDatabase
import com.cleanarchitectureexample.domain.repository.PostRepository
import com.cleanarchitectureexample.domain.model.PostResponse
import javax.inject.Inject

class PostRepositoryImplementation @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) :
    PostRepository {
    override suspend fun getPosts(id: Int): PostResponse = apiService.getPost(id)
    override suspend fun getSavedPosts(): List<PostResponse> = appDatabase.postDao().getAll()
    override suspend fun savePost(post: PostResponse) = appDatabase.postDao().insert(post)
}