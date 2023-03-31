package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.signin

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosSigninBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.AwesomeTodosMainViewModel
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.NavDestination
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.list.TodoListFragment

@AndroidEntryPoint
class TodoSignInFragment : Fragment(R.layout.fragment_awesome_todos_signin), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosSigninBinding

    private val viewModel: TodoSignInViewModel by viewModels()
    private val parentViewModel: AwesomeTodosMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosSigninBinding.bind(view)

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
            it?.run {
                if (this) {
                    parentViewModel.showLoading()
                } else {
                    parentViewModel.hideLoading()
                }
            }
        }

        viewModel.eventSignInSuccess.observe(this) {
            parentViewModel.navigate(
                NavDestination(
                    fragmentClass = TodoListFragment::class.java,
                    addToBackStack = false
                )
            )
        }
    }

    override fun initViews() {
        // Gender
        val genderItems = listOf("Male", "Female")
        val genderAdapter =
            ArrayAdapter(requireContext(), R.layout.awesome_todos_status_list_item, genderItems)
        binding.tvGender.setAdapter(genderAdapter)
    }

    override fun initListeners() {
        binding.btnSignIn.setOnClickListener {
            viewModel.signIn(
                binding.etName.text.toString(),
                binding.etEmail.text.toString(),
                binding.tvGender.text.toString()
            )
        }
    }
}
