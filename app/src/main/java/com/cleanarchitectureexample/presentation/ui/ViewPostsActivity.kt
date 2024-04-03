package com.cleanarchitectureexample.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cleanarchitectureexample.databinding.ActivityViewPostsBinding
import com.cleanarchitectureexample.domain.model.PostResponse
import com.cleanarchitectureexample.presentation.adapter.PostAdapter
import com.cleanarchitectureexample.presentation.viewModel.PostViewModel
import com.cleanarchitectureexample.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ViewPostsActivity : AppCompatActivity() {
    private lateinit var activityViewPostsBinding: ActivityViewPostsBinding
    private lateinit var postAdapter: PostAdapter
    private var postResponseList: MutableList<PostResponse> = mutableListOf()
    private val postViewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewPostsBinding = ActivityViewPostsBinding.inflate(layoutInflater)
        setContentView(activityViewPostsBinding.root)

        initSetup()

        postViewModel.getSavedPosts()
        attachObservers()


    }

    private fun initSetup() {
        postAdapter = PostAdapter(postResponseList)
        activityViewPostsBinding.recyclerView.adapter = postAdapter
        activityViewPostsBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun attachObservers() {

        lifecycleScope.launch {
            postViewModel.responseList.collect {
                when (it) {
                    is Resource.Loading -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ViewPostsActivity, "loading...", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    is Resource.Failed -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ViewPostsActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ViewPostsActivity, "success", Toast.LENGTH_SHORT)
                                .show()
                        }
                        handleSuccess(it.data)
                    }

                    else -> {}
                }
            }

        }


    }

    private fun handleSuccess(it: List<PostResponse>) {

        postResponseList.clear()
        postResponseList.addAll(it.toMutableList())
        postAdapter.notifyDataSetChanged()

    }

}