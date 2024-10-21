package com.example.posthub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.posthub.R
import com.example.posthub.viewmodel.CommentViewModel
import androidx.fragment.app.viewModels

class PostDetailsFragment : Fragment() {

    private val commentViewModel: CommentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)

        val postId = arguments?.getInt("postId") ?: 0
        commentViewModel.getComments(postId).observe(viewLifecycleOwner, Observer { comments ->
            // Update UI with comments
        })

        return view
    }
}
