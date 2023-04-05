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

package org.imaginativeworld.simplemvvm.ui.screens.cms.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.CmsTodoItemBinding
import org.imaginativeworld.simplemvvm.models.todo.TodoItem

class TodoListAdapter(
    private val onClick: (TodoItem) -> Unit
) : ListAdapter<TodoItem, TodoListAdapter.TodoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    class TodoViewHolder private constructor(
        private val binding: CmsTodoItemBinding,
        private val onClick: (TodoItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoItem) {
            binding.tvTitle.text = item.title
            binding.tvDueDate.text = "Due: ${item.getDueDate()}"
            binding.tvStatus.text = item.getStatusLabel()
            binding.root.context?.let { context ->
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        item.getStatusColor()
                    )
                )
            }

            binding.root.setOnClickListener {
                onClick(item)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onClick: (TodoItem) -> Unit
            ): TodoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CmsTodoItemBinding.inflate(layoutInflater, parent, false)
                return TodoViewHolder(binding, onClick)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TodoItem>() {
            override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}