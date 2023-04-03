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
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentDemoFragmentNavListBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.fragmentNav.viewmodel.DemoFragmentNavViewModel
import timber.log.Timber

class DemoFragmentNavList : Fragment(R.layout.fragment_demo_fragment_nav_list), CommonFunctions {

    private val parentViewModel: DemoFragmentNavViewModel by viewModels(ownerProducer = {
        requireParentFragment()
    })

    private lateinit var binding: FragmentDemoFragmentNavListBinding

    private var items = listOf(
        "🍎 Red Apple",
        "🍏 Green Apple",
        "🍊 Orange",
        "🍌 Banana",
        "🥭 Mango",
        "🍐 Papaya",
        "🍍 Pineapple",
        "🍈 Melon",
        "🍉 Watermelon",
        "🍊 Tangerine",
        "🍋 Lemon",
        "🍐 Pear",
        "🍑 Peach",
        "🍒 Cherries",
        "🍓 Strawberry",
        "🫐 Blueberries",
        "🥝 Kiwi Fruit"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDemoFragmentNavListBinding.bind(view)

        // Pass data between fragments: using `Bundle`.
        arguments?.apply {
            val dataOne = getInt("data_one")
            Timber.d("dataOne: $dataOne")
        }

        initObservers()

        initViews()

        initListeners()
    }

    override fun initObservers() {
        // Pass data between fragments: using `FragmentManager`.
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val action = bundle.getString(PARAM_ACTION)

            if (action == PARAM_ACTION_SHUFFLE) {
                items = items.shuffled()

                initAdapter()
            }
        }
    }

    override fun initViews() {
        initAdapter()
    }

    private fun initAdapter() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        binding.list.adapter = adapter
    }

    override fun initListeners() {
        binding.list.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            parentViewModel.selectItem(
                items[position]
            )
        }
    }

    companion object {
        const val PARAM_ACTION = "action"
        const val PARAM_ACTION_SHUFFLE = "shuffle"

        const val REQUEST_KEY = "request_key_nav_list"
    }
}
