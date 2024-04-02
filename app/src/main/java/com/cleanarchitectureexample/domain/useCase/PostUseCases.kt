package com.cleanarchitectureexample.domain.useCase


import com.cleanarchitectureexample.domain.model.PostResponse
import com.cleanarchitectureexample.domain.repository.PostRepository
import com.cleanarchitectureexample.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class PostUseCases @Inject constructor(private val postRepository: PostRepository) {

    fun getPosts(id: Int): Flow<Resource<PostResponse>> = flow {
        emit(Resource.loading())
        emit(Resource.success(postRepository.getPosts(id)))
    }.catch {
        val error = when (it) {
            is HttpException -> {
                it.message() + "~" + it.code()
            }

            else -> {
                it.message
            }
        }
        emit(Resource.failed(error!!))
    }.flowOn(Dispatchers.IO)
}