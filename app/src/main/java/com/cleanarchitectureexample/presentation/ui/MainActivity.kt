package com.cleanarchitectureexample.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.cleanarchitectureexample.R
import com.cleanarchitectureexample.databinding.ActivityMainBinding
import com.cleanarchitectureexample.presentation.viewModel.PostViewModel
import com.cleanarchitectureexample.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    private val postViewModel: PostViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        attachObservers()
        clickEvents()
    }

    private fun clickEvents() {
        activityMainBinding.go.setOnClickListener {
            if (activityMainBinding.text.text.toString().isEmpty()) {
                activityMainBinding.text.error = "Please enter Id"
                activityMainBinding.text.requestFocus()
            } else {
                val id: Int = activityMainBinding.text.text.toString().toInt()

                if (id in 1..100) {
                    getPosts(id)
                } else {
                    activityMainBinding.text.setText("")

                    Toast.makeText(
                        this@MainActivity,
                        "Please enter a number in range of 1-100",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    private fun attachObservers() {

        lifecycleScope.launch {
            postViewModel.response.collect {
                when (it) {
                    is Resource.Loading -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Failed -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, it.data.title, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getPosts(id: Int) {
        postViewModel.getPosts(id)
    }
}