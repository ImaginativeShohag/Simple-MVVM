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

package org.imaginativeworld.simplemvvm.ui.screens.cms.post.edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentCmsPostEditBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.cms.CMSMainViewModel
import org.imaginativeworld.simplemvvm.utils.extensions.hideKeyboard

@AndroidEntryPoint
class PostEditFragment : Fragment(R.layout.fragment_cms_post_edit), CommonFunctions {
    private val args: PostEditFragmentArgs by navArgs()

    private lateinit var binding: FragmentCmsPostEditBinding

    private val viewModel: PostEditViewModel by viewModels()
    private val parentViewModel: CMSMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCmsPostEditBinding.bind(view)

        initViews()

        initListeners()

        getDetails()
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

        viewModel.post.observe(this) { post ->
            post?.let {
                binding.etTitle.setText(post.title)
                binding.etBody.setText(post.body)
            }
        }

        viewModel.eventUpdateSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                findNavController().popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.actionBarContainer.actionBar.subtitle = "Edit Post"
    }

    override fun initListeners() {
        binding.btnUpdate.setOnClickListener {
            binding.root.hideKeyboard()

            viewModel.update(
                args.userId,
                args.postId,
                binding.etTitle.text.toString(),
                binding.etBody.text.toString()
            )
        }

        binding.actionBarContainer.actionBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getDetails() {
        viewModel.getDetails(args.postId)
    }
}
