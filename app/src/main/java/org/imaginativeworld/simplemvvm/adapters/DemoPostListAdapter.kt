package org.imaginativeworld.simplemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.DemoItemPostBinding
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.DemoPostResult

class DemoPostListAdapter(
    private val listener: OnObjectListInteractionListener<DemoPostResult>
) : ListAdapter<DemoPostResult, DemoPostListAdapter.ListViewHolder>(DIFF_CALLBACK),
    BindableAdapter<List<DemoPostResult>> {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DemoPostResult>() {
            override fun areItemsTheSame(oldItem: DemoPostResult, newItem: DemoPostResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DemoPostResult, newItem: DemoPostResult): Boolean {
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

    override fun setItems(data: List<DemoPostResult>?) {
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
        private val listener: OnObjectListInteractionListener<DemoPostResult>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DemoPostResult) {
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
                listener: OnObjectListInteractionListener<DemoPostResult>
            ): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DemoItemPostBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding, listener)
            }
        }

    }

}