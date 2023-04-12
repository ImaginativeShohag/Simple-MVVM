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

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentDemoFragmentNavBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.ui.screens.fragmentNav.viewmodel.DemoFragmentNavViewModel
import timber.log.Timber

class DemoFragmentNavFragment : Fragment(R.layout.fragment_demo_fragment_nav), CommonFunctions {

    private val viewModel: DemoFragmentNavViewModel by viewModels()

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentDemoFragmentNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDemoFragmentNavBinding.bind(view)

        listener?.setAppTitle(getString(R.string.app_name))

        listener?.hideLoading()

        // Init fragment programmatically
        if (savedInstanceState == null) {
            val bundle = bundleOf(DemoFragmentNavDetails.ARG_EMPTY_TEXT to "Nothing selected!")

            childFragmentManager.commit {
                setReorderingAllowed(true)
                add(binding.fragmentContainerDetails.id, DemoFragmentNavDetails::class.java, bundle)
            }
        }

        initViews()
    }

    override fun initObservers() {
        viewModel.selectedItem.observe(this) {
            Timber.d("Selected item: $it")
        }
    }

    override fun initViews() {
        /* no-op */
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
