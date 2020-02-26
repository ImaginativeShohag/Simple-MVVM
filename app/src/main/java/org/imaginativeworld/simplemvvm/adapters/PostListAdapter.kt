package org.imaginativeworld.simplemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.ItemPostBinding
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.PostResult

class PostListAdapter(
    private val listener: OnObjectListInteractionListener<PostResult>
) : ListAdapter<PostResult, PostListAdapter.ListViewHolder>(DIFF_CALLBACK),
    BindableAdapter<List<PostResult>> {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostResult>() {
            override fun areItemsTheSame(oldItem: PostResult, newItem: PostResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostResult, newItem: PostResult): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun setItems(data: List<PostResult>?) {
        submitList(data) {
            data?.apply {
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

    class ListViewHolder private constructor(
        private val binding: ItemPostBinding,
        private val listener: OnObjectListInteractionListener<PostResult>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostResult) {
            binding.post = item
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                listener.onClick(adapterPosition, item)
            }

            binding.root.setOnLongClickListener {
                listener.onLongClick(adapterPosition, item)
                true
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                listener: OnObjectListInteractionListener<PostResult>
            ): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding, listener)
            }
        }

    }

}