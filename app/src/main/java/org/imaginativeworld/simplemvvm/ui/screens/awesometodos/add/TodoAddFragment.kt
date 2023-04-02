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

package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.add

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosAddBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.AwesomeTodosMainViewModel
import org.imaginativeworld.simplemvvm.utils.extensions.getYYYYMMDD
import org.imaginativeworld.simplemvvm.utils.extensions.hideKeyboard
import java.util.Date

@AndroidEntryPoint
class TodoAddFragment : Fragment(R.layout.fragment_awesome_todos_add), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosAddBinding

    private val viewModel: TodoAddViewModel by viewModels()
    private val parentViewModel: AwesomeTodosMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    private var selectedDueDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosAddBinding.bind(view)

        initViews()

        initListeners()
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

        viewModel.eventSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.actionBar.tvActionTitle.text = "Add Todo"

        // Status
        val items = listOf("Pending", "Completed")
        val adapter = ArrayAdapter(requireContext(), R.layout.awesome_todos_status_list_item, items)
        binding.tvStatus.setAdapter(adapter)

        binding.tvStatus.setText("Pending", false)
    }

    override fun initListeners() {
        binding.btnSelectDueDate.setOnClickListener {
            showDatePicker()
        }

        binding.tvDueDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnAdd.setOnClickListener {
            binding.root.hideKeyboard()

            viewModel.add(
                binding.etTitle.text.toString(),
                selectedDueDate,
                binding.tvStatus.text?.toString() ?: "",
            )
        }

        binding.actionBar.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun showDatePicker() {
        val constraintsBuilder = CalendarConstraints.Builder()
        constraintsBuilder.setStart(System.currentTimeMillis() - 1000L)
        constraintsBuilder.setEnd(System.currentTimeMillis() + 365L * 24L * 60L * 60L * 1000L)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Due Date")
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDueDate = Date(selection)
            binding.tvDueDate.setText(selectedDueDate.getYYYYMMDD())
        }

        datePicker.show(parentFragmentManager, null)
    }
}
