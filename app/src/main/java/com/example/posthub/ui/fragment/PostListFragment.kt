package com.example.posthub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posthub.data.local.PostEntity
import com.example.posthub.databinding.FragmentPostListBinding
import com.example.posthub.ui.adapter.PostAdapter
import com.example.posthub.viewmodel.PostViewModel

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

        postViewModel.posts.observe(viewLifecycleOwner, Observer { postList ->
            // 当数据变化时，更新 Adapter 数据
            postAdapter = PostAdapter(postList) { post ->
                navigateToPostDetail(post)
            }
            binding.recyclerView.adapter = postAdapter
        })

        postViewModel.refreshPosts() // 加载帖子数据
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToPostDetail(post: PostEntity) {
        val action = PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(post.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
