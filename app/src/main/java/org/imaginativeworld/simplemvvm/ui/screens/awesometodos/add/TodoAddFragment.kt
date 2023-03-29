package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.add

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
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosAddBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.utils.extensions.getYYYYMMDD

@AndroidEntryPoint
class TodoAddFragment : Fragment(R.layout.fragment_awesome_todos_add), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosAddBinding

    private val viewModel: TodoAddViewModel by viewModels()

    private var selectedDueDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosAddBinding.bind(view)

        initViews()

        initListeners()
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

        viewModel.eventSuccess.observe(this) { isSuccess ->
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

        binding.tvStatus.setText("Pending", false)
    }

    override fun initListeners() {
        binding.btnSelectDueDate.setOnClickListener {
            val constraintsBuilder = CalendarConstraints.Builder()
            constraintsBuilder.setStart(System.currentTimeMillis() - 1000L)
            constraintsBuilder.setEnd(System.currentTimeMillis() + 365L * 24L * 60L * 60L * 1000L)

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Due Date")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                selectedDueDate = Date(selection)
                binding.tvDueDate.setText(selectedDueDate.getYYYYMMDD())
            }

            datePicker.show(parentFragmentManager, null)
        }

        binding.btnSave.setOnClickListener {
            if (viewModel.isValid(
                    binding.etTitle.text.toString(),
                    selectedDueDate,
                    binding.tvStatus.text?.toString() ?: ""
                )
            ) {
                viewModel.add(
                    binding.etTitle.text.toString(),
                    selectedDueDate!!,
                    binding.tvStatus.text?.toString() ?: ""
                )
            }
        }
    }
}
