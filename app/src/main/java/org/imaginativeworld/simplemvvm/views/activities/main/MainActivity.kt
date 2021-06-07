package org.imaginativeworld.simplemvvm.views.activities.main

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import org.imaginativeworld.simplemvvm.MyApplication
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.ActivityMainBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.MainActivityExtraOnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.utils.extensions.hideKeyboard
import org.imaginativeworld.simplemvvm.utils.extensions.indefiniteSnackbar
import org.imaginativeworld.simplemvvm.utils.extensions.longSnackbar
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CommonFunctions, OnFragmentInteractionListener,
    MainActivityExtraOnFragmentInteractionListener {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).appGraph.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

        initViews()
        initListeners()
    }

    override fun initViews() {

    }

    override fun initListeners() {

    }

    override fun initObservers() {

    }

    override fun setAppTitle(title: String) {
        setTitle(title)
    }

    override fun gotoFragment(@IdRes destinationResId: Int) {
        hideKeyboard()

        if (navController.currentDestination == null) {

            showLoading()
            navController.navigate(destinationResId)

        } else {

            navController.currentDestination?.let {
                if (it.id != destinationResId) {
                    showLoading()
                    navController.navigate(destinationResId)
                }
            }

        }
    }

    override fun gotoFragment(@IdRes destinationResId: Int, data: Bundle) {
        hideKeyboard()

        if (navController.currentDestination == null) {

            showLoading()
            navController.navigate(destinationResId, data)

        } else {

            navController.currentDestination?.let {
                if (it.id != destinationResId) {
                    showLoading()
                    navController.navigate(destinationResId, data)
                }
            }

        }
    }

    override fun gotoFragment(navDirections: NavDirections) {
        hideKeyboard()

        showLoading()

        navController.navigate(navDirections)
    }

    override fun goBack() {
        hideKeyboard()
        
        onBackPressed()
    }

    override fun showSnackbar(message: String) {
        binding.mainContainer.longSnackbar(message)
    }

    override fun showSnackbar(message: String, buttonText: String, action: (View) -> Unit) {
        binding.mainContainer.indefiniteSnackbar(message, buttonText, action)
    }

    override fun showLoading() {
        val loadingAnimation = binding.loadingView.globalLoadingLayout.animate()
            .alpha(1f)
            .setDuration(200)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    binding.loadingView.globalLoadingLayout.alpha = 0f
                    binding.loadingView.globalLoadingLayout.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })

        loadingAnimation?.start()
    }

    override fun hideLoading() {
        val loadingAnimation = binding.loadingView.globalLoadingLayout.animate()
            .alpha(0f)
            .setDuration(200)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    binding.loadingView.globalLoadingLayout.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}

            })

        loadingAnimation?.start()
    }

    override fun getActivityViewModel(): MainViewModel {
        return viewModel
    }
}
