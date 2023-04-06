/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Simple MVVM
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.ui.screens.cms.post.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentCmsPostListBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.models.Post
import org.imaginativeworld.simplemvvm.ui.screens.cms.CMSMainViewModel

@AndroidEntryPoint
class PostListFragment : Fragment(R.layout.fragment_cms_post_list), CommonFunctions {
    private val args: PostListFragmentArgs by navArgs()

    private lateinit var binding: FragmentCmsPostListBinding

    private val viewModel: PostListViewModel by viewModels()
    private val parentViewModel: CMSMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    private lateinit var adapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = PostListAdapter { post ->
            adapterOnClick(post)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCmsPostListBinding.bind(view)

        initViews()

        initListeners()

        load()
    }

    private fun load() {
        viewModel.getPosts(args.userId)
    }

    override fun initObservers() {
        viewModel.eventShowMessage.observe(this) {
            it?.run {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.eventShowLoading.observe(this) {
            it?.run {
                if (this) {
                    parentViewModel.showLoading()
                } else {
                    parentViewModel.hideLoading()
                }
            }
        }

        viewModel.posts.observe(this) { PostItems ->
            binding.tvEmpty.visibility =
                if (PostItems?.isEmpty() == true) View.VISIBLE else View.GONE

            adapter.submitList(PostItems)
        }

        viewModel.eventSignOutSuccess.observe(this) { isSignedOut ->
            if (isSignedOut) {
                requireActivity().finish()
            }
        }
    }

    override fun initViews() {
        binding.actionBar.tvActionTitle.text = "Posts"

        // Init List
        val layoutManager = LinearLayoutManager(activity)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
    }

    override fun initListeners() {
        binding.btnAdd.setOnClickListener {
            val action =
                PostListFragmentDirections.actionPostListFragmentToPostAddFragment(args.userId)
            findNavController().navigate(action)
        }

        binding.actionBar.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun adapterOnClick(post: Post) {
        val action = PostListFragmentDirections.actionPostListFragmentToPostDetailsFragment(
            args.userId,
            post.id
        )
        findNavController().navigate(action)
    }
}
