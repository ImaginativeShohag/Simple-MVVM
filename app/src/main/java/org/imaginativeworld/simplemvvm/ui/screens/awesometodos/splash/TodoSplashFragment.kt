package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosSplashBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.AwesomeTodosMainViewModel
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.NavDestination
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.list.TodoListFragment
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.signin.TodoSignInFragment

@AndroidEntryPoint
class TodoSplashFragment : Fragment(R.layout.fragment_awesome_todos_splash), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosSplashBinding

    private val viewModel: TodoSplashViewModel by viewModels()
    private val parentViewModel: AwesomeTodosMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosSplashBinding.bind(view)

        initViews()
        initListeners()
        authenticate()
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

        viewModel.eventAuthSuccess.observe(this) { isSuccess ->
            if (isSuccess == true) {
                parentViewModel.navigate(
                    NavDestination(
                        TodoListFragment::class.java
                    )
                )
            } else if (isSuccess == false) {
                parentViewModel.navigate(
                    NavDestination(
                        TodoSignInFragment::class.java
                    )
                )
            }
        }
    }

    override fun initViews() {
        /* no-op */
    }

    private fun authenticate() {
        lifecycleScope.launch {
            delay(2000)

            viewModel.authenticate()
        }
    }
}
