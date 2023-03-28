package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosDetailsBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions

@AndroidEntryPoint
class TodoDetailsFragment : Fragment(R.layout.fragment_awesome_todos_details), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosDetailsBinding

    private val viewModel: TodoDetailsViewModel by viewModels()

    private var todoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            todoId = getInt(ARG_TODO_ID)
        }

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosDetailsBinding.bind(view)

        initViews()

        initListeners()

        getDetails()
    }

    override fun initObservers() {
        viewModel.eventShowMessage.observe(this) {
            it?.run {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.eventShowLoading.observe(this) {
//            it?.run {
//                if (this) {
//                    listener?.showLoading()
//                } else {
//                    listener?.hideLoading()
//                }
//            }
        }

        viewModel.todo.observe(this) { todo ->
            todo?.let {
                binding.tvTitle.text = todo.title
                binding.tvStatus.text = if (todo.completed) "Completed" else "Pending"
                context?.let { context ->
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            context,
                            if (todo.completed) R.color.flat_awesome_green_2 else R.color.flat_red_2
                        )
                    )
                }
            }
        }
    }

    override fun initViews() {
        /* no-op */
    }

    private fun getDetails() {
        viewModel.getDetails(todoId)
    }

    companion object {
        const val ARG_TODO_ID = "todo_id"
    }
}
