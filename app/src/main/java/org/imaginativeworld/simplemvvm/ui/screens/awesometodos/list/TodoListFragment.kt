package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosListBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.AwesomeTodosMainViewModel
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.NavDestination
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.add.TodoAddFragment
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.details.TodoDetailsFragment

@AndroidEntryPoint
class TodoListFragment : Fragment(R.layout.fragment_awesome_todos_list), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosListBinding

    private val viewModel: TodoListViewModel by viewModels()
    private val parentViewModel: AwesomeTodosMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = TodoListAdapter { todo ->
            adapterOnClick(todo)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosListBinding.bind(view)

        initViews()

        initListeners()

        load()
    }

    private fun load() {
        viewModel.getTodos()
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

        viewModel.todoItems.observe(this) { todoItems ->
            binding.tvEmpty.visibility =
                if (todoItems?.isEmpty() == true) View.VISIBLE else View.GONE

            adapter.submitList(todoItems)
        }

        viewModel.eventSignOutSuccess.observe(this) { isSignedOut ->
            if (isSignedOut) {
                requireActivity().finish()
            }
        }
    }

    override fun initViews() {
        val layoutManager = LinearLayoutManager(activity)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
    }

    override fun initListeners() {
        binding.btnAdd.setOnClickListener {
            parentViewModel.navigate(
                NavDestination(
                    TodoAddFragment::class.java
                )
            )
        }

        binding.btnSignOut.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Are you sure you want to sign out?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.signOut()
                }
                .show()
        }
    }

    private fun adapterOnClick(todo: TodoItem) {
        val args = bundleOf(
            TodoDetailsFragment.ARG_TODO_ID to todo.id,
            TodoDetailsFragment.ARG_USER_ID to todo.userId
        )

        parentViewModel.navigate(
            NavDestination(
                TodoDetailsFragment::class.java,
                args
            )
        )
    }
}
