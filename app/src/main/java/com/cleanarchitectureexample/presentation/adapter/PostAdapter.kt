package com.cleanarchitectureexample.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cleanarchitectureexample.R
import com.cleanarchitectureexample.domain.model.PostResponse

class PostAdapter(private val postList: List<PostResponse>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val singleUnit = postList[position]
        holder.title.text = singleUnit.title
        holder.body.text = singleUnit.body
        holder.userId.text = "UserId - " + singleUnit.userId.toString()
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.rv_title)
        val body: TextView = itemView.findViewById(R.id.rv_body)
        val userId: TextView = itemView.findViewById(R.id.rv_userId)
    }
}