package com.example.posthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.data.local.PostEntity
import com.example.posthub.databinding.ItemPostBinding


class PostAdapter(
    private val postList: List<PostEntity>,
    private val onItemClick: (PostEntity) -> Unit // onClick callback
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostEntity) {
            binding.titleTextView.text = post.title
            binding.bodyTextView.text = post.body

            // 设置点击监听器
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

    override fun getItemCount(): Int {
        return postList.size
    }
}
