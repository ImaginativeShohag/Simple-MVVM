package org.imaginativeworld.simplemvvm.ui.screens.awesometodos

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.ActivityAwesomeTodosMainBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.list.TodoListFragment

@AndroidEntryPoint
class AwesomeTodosMainActivity : AppCompatActivity(), CommonFunctions {

    private lateinit var binding: ActivityAwesomeTodosMainBinding

    private val viewModel: AwesomeTodosMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAwesomeTodosMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(binding.navHostFragment.id, TodoListFragment::class.java, null)
            }
        }

        initObservers()
        initViews()
        initListeners()
    }

    override fun initObservers() {
        viewModel.navigate.observe(this) { fragmentClass ->
            fragmentClass?.let { navigate(it) }
        }
    }

    override fun initViews() {
        /* no-op */
    }

    override fun initListeners() {
        /* no-op */
    }

    private fun navigate(fragmentClass: Class<out Fragment>) {
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            add(binding.navHostFragment.id, fragmentClass, null)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}
