package org.imaginativeworld.simplemvvm.views.activities

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.ActivityMainBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.views.fragments.user.UserViewModel
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.design.longSnackbar

class MainActivity : AppCompatActivity(), CommonFunctions, OnFragmentInteractionListener {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun getAppViewModel(): UserViewModel? {
        return null
    }

    override fun gotoFragment(@IdRes destinationResId: Int) {
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
        showLoading()

        navController.navigate(navDirections)
    }

    override fun goBack() {
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
}
