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

package org.imaginativeworld.simplemvvm.ui.screens.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.DemoUserListAdapter
import org.imaginativeworld.simplemvvm.databinding.DemoFragmentUserBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.user.UserEntity

@AndroidEntryPoint
class DemoUserFragment :
    Fragment(),
    CommonFunctions,
    OnObjectListInteractionListener<UserEntity> {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentUserBinding

    private val viewModel: DemoUserViewModel by viewModels()

    private lateinit var adapter: DemoUserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = DemoUserListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DemoFragmentUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.title_users))

        viewModel.getUsers()

        initViews()

        initListeners()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

        viewModel.clearUserObservables()
    }

    override fun initViews() {
        // List
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.context,
            layoutManager.orientation
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        binding.recyclerView.adapter = adapter
    }

    override fun initListeners() {
    }

    override fun initObservers() {
        viewModel.eventShowMessage
            .observe(this) {
                it?.run {
                    listener?.showSnackbar(this)
                }
            }

        viewModel.eventShowLoading
            .observe(this) {
                it?.apply {
                    if (it == true) {
                        listener?.showLoading()
                    } else {
                        listener?.hideLoading()
                    }
                }
            }
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

    override fun onClick(position: Int, dataObject: UserEntity) {
        if (dataObject.isFav) {
            viewModel.removeFromFav(user = dataObject, result = { isSuccess ->
                if (isSuccess) {
                    dataObject.isFav = false
                    adapter.notifyItemChanged(position)
                }
            })
        } else {
            viewModel.addToFav(user = dataObject, result = { isSuccess ->
                if (isSuccess) {
                    dataObject.isFav = true
                    adapter.notifyItemChanged(position)
                }
            })
        }

        addToUserList(onSuccess = {
            // ...
        })
    }

    fun addToUserList(onSuccess: (success: Boolean) -> Unit) {
        // ...
        onSuccess(true)
        // ...
    }

    override fun onLongClick(position: Int, dataObject: UserEntity) {
    }

    override fun showEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.GONE
    }
}
