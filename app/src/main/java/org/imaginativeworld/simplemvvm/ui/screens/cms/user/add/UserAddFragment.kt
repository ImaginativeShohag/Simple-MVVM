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

package org.imaginativeworld.simplemvvm.ui.screens.cms.user.add

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentCmsUserAddBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.cms.CMSMainViewModel
import org.imaginativeworld.simplemvvm.utils.extensions.hideKeyboard

@AndroidEntryPoint
class UserAddFragment : Fragment(R.layout.fragment_cms_user_add), CommonFunctions {

    private lateinit var binding: FragmentCmsUserAddBinding

    private val viewModel: UserAddViewModel by viewModels()
    private val parentViewModel: CMSMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCmsUserAddBinding.bind(view)

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

        viewModel.eventAddUserSuccess.observe(this) {
            findNavController().popBackStack()
        }
    }

    override fun initViews() {
        binding.actionBar.tvActionTitle.text = "Add User"

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
        binding.btnAdd.setOnClickListener {
            binding.root.hideKeyboard()

            viewModel.addUser(
                binding.etName.text.toString(),
                binding.etEmail.text.toString(),
                binding.tvGender.text.toString(),
                binding.tvStatus.text.toString()
            )
        }
    }
}
