package org.imaginativeworld.simplemvvm.views.activities

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.network.ApiClient
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import org.imaginativeworld.simplemvvm.viewmodels.AppViewModel
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.design.longSnackbar

class MainActivity : AppCompatActivity(), CommonFunctions, OnFragmentInteractionListener {

    private lateinit var appViewModel: AppViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        val appRepository = AppRepository(
            ApiClient.getClient(),
            AppDatabase(this)
        )

        appViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AppViewModel(appRepository) as T
            }
        })[AppViewModel::class.java]


        initViews()
        initListeners()
    }

    override fun initViews() {

    }

    override fun initListeners() {

    }

    override fun setAppTitle(title: String) {
        setTitle(title)
    }

    override fun getAppViewModel(): AppViewModel {
        return appViewModel
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
        main_container.longSnackbar(message)
    }

    override fun showSnackbar(message: String, buttonText: String, action: (View) -> Unit) {
        main_container.indefiniteSnackbar(message, buttonText, action)
    }

    override fun showLoading() {
        val loadingAnimation = global_loading_layout.animate()
            .alpha(1f)
            .setDuration(200)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    global_loading_layout.alpha = 0f
                    global_loading_layout.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })

        loadingAnimation?.start()
    }

    override fun hideLoading() {
        val loadingAnimation = global_loading_layout.animate()
            .alpha(0f)
            .setDuration(200)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    global_loading_layout.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}

            })

        loadingAnimation?.start()
    }
}
