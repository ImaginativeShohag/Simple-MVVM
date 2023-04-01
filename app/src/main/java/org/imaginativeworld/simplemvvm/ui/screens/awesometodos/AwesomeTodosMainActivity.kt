package org.imaginativeworld.simplemvvm.ui.screens.awesometodos

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.ActivityAwesomeTodosMainBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.awesometodos.splash.TodoSplashFragment

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
                add(binding.navHostFragment.id, TodoSplashFragment::class.java, null, "splash")
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

        viewModel.isLoadingVisible.observe(this) { isLoadingVisible ->
            binding.loadingView.root.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
        }
    }

    override fun initViews() {
        /* no-op */
    }

    override fun initListeners() {
        /* no-op */
    }

    private fun navigate(destination: NavDestination) {
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            replace(binding.navHostFragment.id, destination.fragmentClass, destination.args)
            setReorderingAllowed(true)

            // Ensuring that the splash fragment gets replaced by the destination fragment.
            if (supportFragmentManager.findFragmentByTag("splash") == null &&
                destination.addToBackStack
            ) {
                addToBackStack(null)
            }
        }
    }
}
