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

package org.imaginativeworld.simplemvvm.ui.screens.cms.todo.list

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
import org.imaginativeworld.simplemvvm.databinding.FragmentCmsTodoListBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.models.todo.Todo
import org.imaginativeworld.simplemvvm.ui.screens.cms.CMSMainViewModel

@AndroidEntryPoint
class TodoListFragment : Fragment(R.layout.fragment_cms_todo_list), CommonFunctions {
    private val args: TodoListFragmentArgs by navArgs()

    private lateinit var binding: FragmentCmsTodoListBinding

    private val viewModel: TodoListViewModel by viewModels()
    private val parentViewModel: CMSMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = TodoListAdapter { todo ->
            adapterOnClick(todo)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCmsTodoListBinding.bind(view)

        initViews()

        initListeners()

        load()
    }

    private fun load() {
        viewModel.getTodos(args.userId)
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

        viewModel.todoItems.observe(this) { todoItems ->
            binding.tvEmpty.visibility =
                if (todoItems?.isEmpty() == true) View.VISIBLE else View.GONE

            adapter.submitList(todoItems)
        }

        viewModel.eventSignOutSuccess.observe(this) { isSignedOut ->
            if (isSignedOut) {
                requireActivity().finish()
            }
        }
    }

    override fun initViews() {
        binding.actionBar.tvActionTitle.text = "Todos"

        // Init List
        val layoutManager = LinearLayoutManager(activity)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
    }

    override fun initListeners() {
        binding.btnAdd.setOnClickListener {
            val action =
                TodoListFragmentDirections.actionTodoListFragmentToTodoAddFragment(args.userId)
            findNavController().navigate(action)
        }

        binding.actionBar.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun adapterOnClick(todo: Todo) {
        val action = TodoListFragmentDirections.actionTodoListFragmentToTodoDetailsFragment(
            args.userId,
            todo.id
        )
        findNavController().navigate(action)
    }
}
