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

package org.imaginativeworld.simplemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.DemoLoadStateFooterViewItemBinding

class DemoPostPagedLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<DemoPostPagedLoadStateAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return ItemViewHolder.from(parent, retry)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class ItemViewHolder private constructor(
        private val binding: DemoLoadStateFooterViewItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun from(
                parent: ViewGroup,
                retry: () -> Unit
            ): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    DemoLoadStateFooterViewItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding, retry)
            }
        }
    }
}
