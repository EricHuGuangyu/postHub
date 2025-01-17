package com.example.posthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.data.local.CommentEntity
import com.example.posthub.databinding.ItemCommentBinding

class CommentAdapter(
    private var commentList: List<CommentEntity>,
    private val fullCommentList: List<CommentEntity>
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentEntity) {
            binding.nameTextView.text = comment.name
            binding.emailTextView.text = comment.email
            binding.bodyTextView.text = comment.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun filter(query: String) {
        // If search view is empty return full list
        commentList = if (query.isEmpty()) {
            fullCommentList
        } else {
            // filter with name and body
            fullCommentList.filter { comment ->
                comment.name.contains(query, ignoreCase = true) ||
                        comment.body.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}
