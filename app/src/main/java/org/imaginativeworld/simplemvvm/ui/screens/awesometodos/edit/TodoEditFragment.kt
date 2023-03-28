package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.edit

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosEditBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions

@AndroidEntryPoint
class TodoEditFragment : Fragment(R.layout.fragment_awesome_todos_edit), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosEditBinding

    private val viewModel: TodoEditViewModel by viewModels()

    private var todoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            todoId = getInt(ARG_TODO_ID)
        }

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosEditBinding.bind(view)

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
                binding.etTitle.setText(todo.title)
                binding.tvStatus.setText(if (todo.completed) "Completed" else "Pending", false)
            }
        }

        viewModel.eventUpdateSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun initViews() {
        // Status
        val items = listOf("Pending", "Completed")
        val adapter = ArrayAdapter(requireContext(), R.layout.awesome_todos_status_list_item, items)
        binding.tvStatus.setAdapter(adapter)
    }

    override fun initListeners() {
//        binding.btnSelectDueDate.setOnClickListener {
//            val datePicker = MaterialDatePicker.Builder.datePicker()
//                .setTitleText("Due Date")
//                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                .build()
//            datePicker.show(parentFragmentManager, null)
//        }

        binding.btnUpdate.setOnClickListener {
            if (viewModel.isValid(
                    binding.etTitle.text.toString(),
                    binding.tvStatus.text?.toString() ?: ""
                )
            ) {
                viewModel.update(
                    todoId,
                    binding.etTitle.text.toString(),
                    binding.tvStatus.text?.toString() ?: ""
                )
            }
        }
    }

    private fun getDetails() {
        viewModel.getDetails(todoId)
    }

    companion object {
        const val ARG_TODO_ID = "todo_id"
    }
}
