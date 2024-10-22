package com.example.posthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.data.local.PostEntity
import com.example.posthub.databinding.ItemPostBinding


class PostAdapter(
    private var postList: List<PostEntity>,
    private var fullPostList: List<PostEntity>,
    private val onItemClick: (PostEntity) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostEntity) {
            binding.titleTextView.text = post.title
            binding.bodyTextView.text = post.body

            // onItem click
            binding.root.setOnClickListener {
                onItemClick(post) // 触发回调
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    fun filter(query: String) {
        // If search view is empty return full list
        postList = if (query.isEmpty()) {
            fullPostList
        } else {
            // filter with title
            fullPostList.filter { post ->
                post.title.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
