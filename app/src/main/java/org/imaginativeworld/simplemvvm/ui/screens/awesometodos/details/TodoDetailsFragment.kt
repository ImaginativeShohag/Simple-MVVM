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

package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosDetailsBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.AwesomeTodosMainViewModel
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.NavDestination
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.edit.TodoEditFragment

@AndroidEntryPoint
class TodoDetailsFragment : Fragment(R.layout.fragment_awesome_todos_details), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosDetailsBinding

    private val viewModel: TodoDetailsViewModel by viewModels()
    private val parentViewModel: AwesomeTodosMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    private var userId: Int = 0
    private var todoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            userId = getInt(ARG_USER_ID)
            todoId = getInt(ARG_TODO_ID)
        }

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosDetailsBinding.bind(view)

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

        viewModel.todo.observe(this) { todo ->
            todo?.let {
                binding.tvTitle.text = todo.title
                binding.tvStatus.text = todo.getStatusLabel()
                context?.let { context ->
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            context,
                            todo.getStatusColor()
                        )
                    )
                }
                binding.tvDueDate.text = "Due: ${todo.getDueDate()}"
            }
        }

        viewModel.eventDeleteSuccess.observe(this) { isSuccess ->
            if (isSuccess == true) {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.actionBar.tvActionTitle.text = "Todo Details"
    }

    override fun initListeners() {
        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Are you sure you want to delete this todo?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteTodo(todoId)
                }
                .show()
        }

        binding.btnEdit.setOnClickListener {
            val args = bundleOf(
                ARG_TODO_ID to todoId,
                ARG_USER_ID to userId
            )

            parentViewModel.navigate(
                NavDestination(
                    TodoEditFragment::class.java,
                    args
                )
            )
        }

        binding.actionBar.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun getDetails() {
        viewModel.getDetails(todoId)
    }

    companion object {
        const val ARG_TODO_ID = "todo_id"
        const val ARG_USER_ID = "user_id"
    }
}
