package org.imaginativeworld.simplemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.ItemUserBinding
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.UserEntity

class UserListAdapter(
    val listener: OnObjectListInteractionListener<UserEntity>
) :
    ListAdapter<UserEntity, UserListAdapter.ListViewHolder>(DIFF_CALLBACK),
    BindableAdapter<List<UserEntity>> {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun setItems(data: List<UserEntity>?) {
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
        private val binding: ItemUserBinding,
        private val listener: OnObjectListInteractionListener<UserEntity>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserEntity) {
            binding.user = item
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
                listener: OnObjectListInteractionListener<UserEntity>
            ): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding, listener)
            }
        }

    }

}