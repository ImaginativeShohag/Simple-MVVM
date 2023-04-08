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

package org.imaginativeworld.simplemvvm.ui.screens.cms.comment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.CmsCommentItemBinding
import org.imaginativeworld.simplemvvm.models.Comment

class CommentListAdapter(
    private val onClick: (Comment) -> Unit
) : PagingDataAdapter<Comment, CommentListAdapter.CommentViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val Comment = getItem(position)
        holder.bind(Comment)
    }

    class CommentViewHolder private constructor(
        private val binding: CmsCommentItemBinding,
        private val onClick: (Comment) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment?) {
            item?.also {
                binding.tvName.text = item.name
                binding.tvEmail.text = item.email
                binding.tvBody.text = item.body

                binding.root.setOnClickListener {
                    onClick(item)
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onClick: (Comment) -> Unit
            ): CommentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CmsCommentItemBinding.inflate(layoutInflater, parent, false)
                return CommentViewHolder(binding, onClick)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
