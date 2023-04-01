package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.edit

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosEditBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.AwesomeTodosMainViewModel
import org.imaginativeworld.simplemvvm.utils.extensions.getYYYYMMDD
import org.imaginativeworld.simplemvvm.utils.extensions.hideKeyboard

@AndroidEntryPoint
class TodoEditFragment : Fragment(R.layout.fragment_awesome_todos_edit), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosEditBinding

    private val viewModel: TodoEditViewModel by viewModels()
    private val parentViewModel: AwesomeTodosMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    private var selectedDueDate: Date? = null

    private var userId: Int = 0
    private var todoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            userId = getInt(ARG_USER_ID)
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
            it?.run {
                if (this) {
                    parentViewModel.showLoading()
                } else {
                    parentViewModel.hideLoading()
                }
            }
        }

        viewModel.todo.observe(this) { todo ->
            todo?.let {
                binding.etTitle.setText(todo.title)
                binding.tvDueDate.setText(todo.dueOn.getYYYYMMDD())
                binding.tvStatus.setText(todo.getStatusLabel(), false)

                selectedDueDate = todo.dueOn
            }
        }

        viewModel.eventUpdateSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.actionBar.tvActionTitle.text = "Edit Todo"

        // Status
        val items = listOf("Pending", "Completed")
        val adapter = ArrayAdapter(requireContext(), R.layout.awesome_todos_status_list_item, items)
        binding.tvStatus.setAdapter(adapter)
    }

    override fun initListeners() {
        binding.btnSelectDueDate.setOnClickListener {
            showDatePicker()
        }

        binding.tvDueDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnUpdate.setOnClickListener {
            binding.root.hideKeyboard()

            viewModel.update(
                userId,
                todoId,
                binding.etTitle.text.toString(),
                selectedDueDate!!,
                binding.tvStatus.text?.toString() ?: ""
            )
        }

        binding.actionBar.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun showDatePicker() {
        val constraintsBuilder = CalendarConstraints.Builder()
        constraintsBuilder.setStart(System.currentTimeMillis() - 1000L)
        constraintsBuilder.setEnd(System.currentTimeMillis() + 365L * 24L * 60L * 60L * 1000L)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Due Date")
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(selectedDueDate?.time ?: MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDueDate = Date(selection)
            binding.tvDueDate.setText(selectedDueDate.getYYYYMMDD())
        }

        datePicker.show(parentFragmentManager, null)
    }

    private fun getDetails() {
        viewModel.getDetails(todoId)
    }

    companion object {
        const val ARG_TODO_ID = "todo_id"
        const val ARG_USER_ID = "user_id"
    }
}
