package org.imaginativeworld.simplemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import org.imaginativeworld.simplemvvm.databinding.DemoItemPostBinding
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.DemoPost
import org.imaginativeworld.simplemvvm.utils.extensions.dpToPx

class DemoPostListAdapter(
    private val listener: OnObjectListInteractionListener<DemoPost>
) : ListAdapter<DemoPost, DemoPostListAdapter.ListViewHolder>(DIFF_CALLBACK),
    BindableAdapter<List<DemoPost>> {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DemoPost>() {
            override fun areItemsTheSame(oldItem: DemoPost, newItem: DemoPost): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DemoPost, newItem: DemoPost): Boolean {
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

    override fun setItems(data: List<DemoPost>?) {
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
        private val listener: OnObjectListInteractionListener<DemoPost>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DemoPost) {
            binding.post = item
            binding.executePendingBindings()

            // Image
            val imageUrl = "https://picsum.photos/seed/${item.id}/128"
            binding.img.load(imageUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8.dpToPx().toFloat()))
            }

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
                listener: OnObjectListInteractionListener<DemoPost>
            ): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DemoItemPostBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding, listener)
            }
        }

    }

}