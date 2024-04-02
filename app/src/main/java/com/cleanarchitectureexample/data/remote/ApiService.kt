package com.cleanarchitectureexample.data.remote

import com.cleanarchitectureexample.domain.model.PostResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): PostResponse
}