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

package org.imaginativeworld.simplemvvm.ui.screens.cms.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentCmsUserListBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.models.awesometodos.User
import org.imaginativeworld.simplemvvm.ui.screens.cms.CMSMainViewModel
import org.imaginativeworld.simplemvvm.utils.extensions.hide

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_cms_user_list), CommonFunctions {
    private lateinit var binding: FragmentCmsUserListBinding

    private val viewModel: UserListViewModel by viewModels()
    private val parentViewModel: CMSMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = UserListAdapter { todo ->
            adapterOnClick(todo)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCmsUserListBinding.bind(view)

        initViews()
        initListeners()
        initAdapterObserver()
        load()
    }

    private fun load() {
        lifecycleScope.launch {
            viewModel.getUsers().collectLatest(adapter::submitData)
        }
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
    }

    override fun initViews() {
        val layoutManager = LinearLayoutManager(activity)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter

        binding.actionBar.btnBack.hide()
    }

    override fun initListeners() {
        binding.btnAdd.setOnClickListener {
//            parentViewModel.navigate(
//                NavDestination(
//                    TodoAddFragment::class.java
//                )
//            )
        }
    }

    private fun adapterOnClick(user: User) {
//        val args = bundleOf(
//            TodoDetailsFragment.ARG_TODO_ID to todo.id,
//            TodoDetailsFragment.ARG_USER_ID to todo.userId
//        )
//
//        parentViewModel.navigate(
//            NavDestination(
//                TodoDetailsFragment::class.java,
//                args
//            )
//        )
    }

    private fun initAdapterObserver() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                val isLoading = loadState.refresh is LoadState.Loading

                binding.tvEmpty.isVisible = isListEmpty
                if (isLoading) {
                    parentViewModel.showLoading()
                } else {
                    parentViewModel.hideLoading()
                }
            }
        }
    }
}
