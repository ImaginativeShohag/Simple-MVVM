package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.databinding.AwesomeTodosTodoItemBinding
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem

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
        private val binding: AwesomeTodosTodoItemBinding,
        private val onClick: (TodoItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoItem) {
            binding.tvTitle.text = item.title
            binding.tvStatus.text = if (item.completed) "Completed" else "Pending"

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
                val binding = AwesomeTodosTodoItemBinding.inflate(layoutInflater, parent, false)
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
