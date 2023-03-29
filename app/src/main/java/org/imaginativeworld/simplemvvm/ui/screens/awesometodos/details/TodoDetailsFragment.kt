package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosDetailsBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.AwesomeTodosMainViewModel
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.NavDestination
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.edit.TodoEditFragment

@AndroidEntryPoint
class TodoDetailsFragment : Fragment(R.layout.fragment_awesome_todos_details), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosDetailsBinding

    private val viewModel: TodoDetailsViewModel by viewModels()
    private val parentViewModel: AwesomeTodosMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

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
                binding.tvStatus.text = todo.getStatusLabel()
                context?.let { context ->
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            context,
                            todo.getStatusColor()
                        )
                    )
                }
                binding.tvDueDate.text = "Due: ${todo.getDueDate()}"
            }
        }

        viewModel.eventDeleteSuccess.observe(this) { isSuccess ->
            if (isSuccess == true) {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.btnDelete.setOnClickListener {
            viewModel.deleteTodo(todoId)
        }

        binding.btnEdit.setOnClickListener {
            val args = bundleOf(TodoEditFragment.ARG_TODO_ID to todoId)

            parentViewModel.navigate(
                NavDestination(
                    TodoEditFragment::class.java,
                    args
                )
            )
        }
    }

    private fun getDetails() {
        viewModel.getDetails(todoId)
    }

    companion object {
        const val ARG_TODO_ID = "todo_id"
    }
}
