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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.DemoItemUserBinding
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.DemoUserEntity

class DemoUserListAdapter(
    val listener: OnObjectListInteractionListener<DemoUserEntity>,
) :
    ListAdapter<DemoUserEntity, DemoUserListAdapter.ListViewHolder>(DIFF_CALLBACK),
    BindableAdapter<List<DemoUserEntity>> {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DemoUserEntity>() {
            override fun areItemsTheSame(oldItem: DemoUserEntity, newItem: DemoUserEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DemoUserEntity, newItem: DemoUserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun setItems(data: List<DemoUserEntity>?) {
        data?.apply {
            submitList(this) {
                checkEmptiness()
            }
        }
    }

    private fun checkEmptiness() {
        if (itemCount > 0) {
            listener.hideEmptyView()
        } else {
            listener.showEmptyView()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ListViewHolder private constructor(
        private val binding: DemoItemUserBinding,
        private val listener: OnObjectListInteractionListener<DemoUserEntity>,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DemoUserEntity) {
            binding.user = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onClick(bindingAdapterPosition, item)
            }
            binding.root.setOnLongClickListener {
                listener.onLongClick(bindingAdapterPosition, item)

                true
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                listener: OnObjectListInteractionListener<DemoUserEntity>,
            ): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DemoItemUserBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding, listener)
            }
        }
    }
}
