package com.example.posthub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posthub.data.local.PostEntity
import com.example.posthub.databinding.FragmentPostListBinding
import com.example.posthub.ui.adapter.PostAdapter
import com.example.posthub.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : Fragment() {

    private var _binding: FragmentPostListBinding? = null
    private val binding get() = _binding!!

    private val postViewModel: PostViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        //update UI and onClick lambda
        postViewModel.posts.observe(viewLifecycleOwner, Observer { postList ->
            postAdapter = PostAdapter(postList, postList) { post ->
                navigateToPostDetail(post)
            }
            binding.recyclerViewPost.adapter = postAdapter
        })
        // add post list data
        postViewModel.refreshPosts()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewPost.layoutManager = LinearLayoutManager(requireContext())
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                postAdapter.filter(newText ?: "")
                return true
            }
        })
    }

    private fun navigateToPostDetail(post: PostEntity) {
        val action = PostListFragmentDirections.actionPostListFragmentToPostDetailsFragment(post.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
