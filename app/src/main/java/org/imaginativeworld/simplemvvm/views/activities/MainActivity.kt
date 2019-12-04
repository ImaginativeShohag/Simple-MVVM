package org.imaginativeworld.simplemvvm.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
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

    }

    override fun hideLoading() {

    }
}
