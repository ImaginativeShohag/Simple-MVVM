package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.add

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosAddBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions

@AndroidEntryPoint
class TodoAddFragment : Fragment(R.layout.fragment_awesome_todos_add), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosAddBinding

    private val viewModel: TodoAddViewModel by viewModels()

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
//        binding.btnSelectDueDate.setOnClickListener {
//            val datePicker = MaterialDatePicker.Builder.datePicker()
//                .setTitleText("Due Date")
//                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                .build()
//            datePicker.show(parentFragmentManager, null)
//        }

        binding.btnSave.setOnClickListener {
            if (viewModel.isValid(
                    binding.etTitle.text.toString(),
                    binding.tvStatus.text?.toString() ?: ""
                )
            ) {
                viewModel.add(
                    binding.etTitle.text.toString(),
                    binding.tvStatus.text?.toString() ?: ""
                )
            }
        }
    }
}
