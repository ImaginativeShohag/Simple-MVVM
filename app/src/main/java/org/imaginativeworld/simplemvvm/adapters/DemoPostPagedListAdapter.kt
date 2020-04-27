package org.imaginativeworld.simplemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.DemoItemPostBinding
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.PostResult

class DemoPostPagedListAdapter(
    private val listener: OnObjectListInteractionListener<PostResult>
) : PagedListAdapter<PostResult, DemoPostPagedListAdapter.ListViewHolder>(DIFF_CALLBACK),
    BindableAdapter<PagedList<PostResult>> {

    companion object {

        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<PostResult>() {
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

    override fun setItems(data: PagedList<PostResult>?) {

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
        private val binding: DemoItemPostBinding,
        private val listener: OnObjectListInteractionListener<PostResult>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostResult?) {
            item?.also { _item ->
                binding.post = _item
                binding.executePendingBindings()

                binding.root.setOnClickListener {
                    listener.onClick(adapterPosition, _item)
                }

                binding.root.setOnLongClickListener {
                    listener.onLongClick(adapterPosition, _item)
                    true
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                listener: OnObjectListInteractionListener<PostResult>
            ): DemoPostPagedListAdapter.ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DemoItemPostBinding.inflate(layoutInflater, parent, false)
                return DemoPostPagedListAdapter.ListViewHolder(binding, listener)
            }
        }

    }

}