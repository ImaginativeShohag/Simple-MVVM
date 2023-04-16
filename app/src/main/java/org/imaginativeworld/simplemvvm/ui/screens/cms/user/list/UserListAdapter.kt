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

package org.imaginativeworld.simplemvvm.ui.screens.cms.user.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.CmsUserItemBinding
import org.imaginativeworld.simplemvvm.models.user.User
import org.imaginativeworld.simplemvvm.utils.setProfileImageFromUrl

@SuppressLint("SetTextI18n")
class UserListAdapter(
    private val onClick: (User) -> Unit
) : PagingDataAdapter<User, UserListAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    class UserViewHolder private constructor(
        private val binding: CmsUserItemBinding,
        private val onClick: (User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User?) {
            item?.also {
                binding.tvId.text = "#${item.id}"
                binding.tvName.text = item.name
                binding.tvEmail.text = item.email
                binding.tvGender.text = item.gender
                binding.tvStatus.text = item.getStatusLabel()
                binding.root.context?.let { context ->
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            context,
                            item.getStatusColor()
                        )
                    )
                }
                binding.img.setProfileImageFromUrl("https://picsum.photos/200/200?${item.id}")

                binding.root.setOnClickListener {
                    onClick(item)
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onClick: (User) -> Unit
            ): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CmsUserItemBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding, onClick)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
