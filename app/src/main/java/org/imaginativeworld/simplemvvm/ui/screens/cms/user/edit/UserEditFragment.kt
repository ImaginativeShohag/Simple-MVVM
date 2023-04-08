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

package org.imaginativeworld.simplemvvm.ui.screens.cms.user.edit

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentCmsUserEditBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.cms.CMSMainViewModel
import org.imaginativeworld.simplemvvm.utils.extensions.hideKeyboard

@AndroidEntryPoint
class UserEditFragment : Fragment(R.layout.fragment_cms_user_edit), CommonFunctions {
    private val args: UserEditFragmentArgs by navArgs()

    private lateinit var binding: FragmentCmsUserEditBinding

    private val viewModel: UserEditViewModel by viewModels()
    private val parentViewModel: CMSMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCmsUserEditBinding.bind(view)

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

        viewModel.user.observe(this) { todo ->
            todo?.let {
                binding.etName.setText(todo.name)
                binding.etEmail.setText(todo.email)
                binding.tvGender.setText(todo.getGenderLabel(), false)
                binding.tvStatus.setText(todo.getStatusLabel(), false)
            }
        }

        viewModel.eventUpdateSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                findNavController().popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.actionBar.tvActionTitle.text = "Edit User"

        // Gender
        val genderItems = listOf("Male", "Female")
        val genderAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner_default, genderItems)
        binding.tvGender.setAdapter(genderAdapter)

        // Status
        val statusItems = listOf("Active", "Inactive")
        val statusAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner_default, statusItems)
        binding.tvStatus.setAdapter(statusAdapter)
    }

    override fun initListeners() {
        binding.actionBar.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnUpdate.setOnClickListener {
            binding.root.hideKeyboard()

            viewModel.update(
                args.userId,
                binding.etName.text.toString(),
                binding.etEmail.text.toString(),
                binding.tvGender.text.toString(),
                binding.tvStatus.text.toString()
            )
        }
    }

    private fun getDetails() {
        viewModel.getDetails(args.userId)
    }
}
