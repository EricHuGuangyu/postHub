package com.example.posthub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.posthub.viewmodel.CommentViewModel
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posthub.databinding.FragmentPostDetailsBinding
import com.example.posthub.ui.adapter.CommentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {
    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    private val commentViewModel: CommentViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        val args: PostDetailsFragmentArgs by navArgs()
        val postId = args.postId
        commentViewModel.comments.observe(viewLifecycleOwner, Observer { comments ->
            // Update UI with comments
            commentAdapter = CommentAdapter(comments, comments)
            binding.recyclerViewComments.adapter = commentAdapter
        })
        commentViewModel.refreshComments(postId)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewComments.layoutManager = LinearLayoutManager(requireContext())
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                commentAdapter.filter(newText ?: "")
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
