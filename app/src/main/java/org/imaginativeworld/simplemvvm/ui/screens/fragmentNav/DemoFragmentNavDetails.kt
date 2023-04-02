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

package org.imaginativeworld.simplemvvm.ui.screens.fragmentNav

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentDemoFragmentNavDetailsBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.fragmentNav.viewmodel.DemoFragmentNavViewModel

class DemoFragmentNavDetails :
    Fragment(R.layout.fragment_demo_fragment_nav_details),
    CommonFunctions {

    private val parentViewModel: DemoFragmentNavViewModel by viewModels(ownerProducer = {
        requireParentFragment()
    })

    private lateinit var binding: FragmentDemoFragmentNavDetailsBinding

    private var emptyText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            emptyText = getString(ARG_EMPTY_TEXT, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDemoFragmentNavDetailsBinding.bind(view)

        initObservers()

        initViews()

        initListeners()
    }

    override fun initObservers() {
        // Pass data between fragments: using parent `ViewModel`.
        parentViewModel.selectedItem.observe(this.viewLifecycleOwner) { text ->
            binding.textView.text = text
        }
    }

    override fun initViews() {
        binding.textView.text = emptyText
    }

    override fun initListeners() {
        binding.btnShuffle.setOnClickListener {
            setFragmentResult(
                DemoFragmentNavList.REQUEST_KEY,
                bundleOf(
                    DemoFragmentNavList.PARAM_ACTION to DemoFragmentNavList.PARAM_ACTION_SHUFFLE,
                ),
            )
        }
    }

    companion object {
        const val ARG_EMPTY_TEXT = "empty_text"
    }
}
