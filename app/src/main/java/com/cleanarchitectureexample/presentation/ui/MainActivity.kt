package com.cleanarchitectureexample.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.cleanarchitectureexample.databinding.ActivityMainBinding
import com.cleanarchitectureexample.domain.model.PostResponse
import com.cleanarchitectureexample.presentation.viewModel.PostViewModel
import com.cleanarchitectureexample.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val postViewModel: PostViewModel by viewModels()
    private lateinit var post: PostResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        attachObservers()
        clickEvents()
    }

    private fun clickEvents() {

        activityMainBinding.crossBtn.setOnClickListener {
            activityMainBinding.postLayout.visibility = View.GONE
            activityMainBinding.textView.setText("")
        }

        activityMainBinding.saveBtn.setOnClickListener {
            lifecycleScope.launch {
                savePost()
            }
        }
        activityMainBinding.button.setOnClickListener {
            if (activityMainBinding.textView.text.toString().isEmpty()) {
                activityMainBinding.textView.error = "Please enter Id"
                activityMainBinding.textView.requestFocus()
            } else {
                val id: Int = activityMainBinding.textView.text.toString().toInt()

                if (id in 1..100) {
                    getPosts(id)
                } else {
                    activityMainBinding.textView.setText("")

                    Toast.makeText(
                        this@MainActivity,
                        "Please enter a number in range of 1-100",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

        activityMainBinding.viewBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, ViewPostsActivity::class.java))
        }
    }

    private fun attachObservers() {

        lifecycleScope.launch {
            postViewModel.response.collect {
                when (it) {
                    is Resource.Loading -> {
//                        showLoad()
                    }

                    is Resource.Failed -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Success -> {
                        handleSuccess(it.data)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun handleSuccess(data: PostResponse) {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        activityMainBinding.userId.text = "UserId - ${data.userId}"
        activityMainBinding.title.text = data.title
        activityMainBinding.body.text = data.body

        post = data
        activityMainBinding.postLayout.visibility = View.VISIBLE
    }

    private suspend fun savePost() {
        val def = lifecycleScope.async {
            postViewModel.savePost(PostResponse(post.body, post.title, post.userId))
        }
        def.await()
        Toast.makeText(
            this@MainActivity,
            "Post saved successfully",
            Toast.LENGTH_SHORT
        ).show()
        activityMainBinding.postLayout.visibility = View.GONE
        activityMainBinding.textView.setText("")
    }


    private fun getPosts(id: Int) {
        postViewModel.getPosts(id)
    }
}