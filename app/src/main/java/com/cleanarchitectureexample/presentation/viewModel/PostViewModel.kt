package com.cleanarchitectureexample.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitectureexample.domain.model.PostResponse
import com.cleanarchitectureexample.domain.useCase.PostUseCases
import com.cleanarchitectureexample.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postUseCases: PostUseCases) : ViewModel() {

    private var _response: MutableStateFlow<Resource<PostResponse>?> = MutableStateFlow(null)
    var response: StateFlow<Resource<PostResponse>?> = _response

    fun getPosts(id: Int) {
        viewModelScope.launch {
            postUseCases.getPosts(id).collect {
                _response.value = it
            }
        }
    }
}