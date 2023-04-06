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

package org.imaginativeworld.simplemvvm.ui.screens.cms.post.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.CmsPostItemBinding
import org.imaginativeworld.simplemvvm.models.Post

class PostListAdapter(
    private val onClick: (Post) -> Unit
) : PagingDataAdapter<Post, PostListAdapter.PostViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostViewHolder private constructor(
        private val binding: CmsPostItemBinding,
        private val onClick: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post?) {
            item?.also {
                binding.tvTitle.text = item.title
                binding.tvBody.text = item.body

                binding.root.setOnClickListener {
                    onClick(item)
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onClick: (Post) -> Unit
            ): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CmsPostItemBinding.inflate(layoutInflater, parent, false)
                return PostViewHolder(binding, onClick)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
