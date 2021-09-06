package org.imaginativeworld.simplemvvm.ui.activities.main

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.ActivityMainBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.MainActivityExtraOnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.models.Event
import org.imaginativeworld.simplemvvm.utils.SharedPref
import org.imaginativeworld.simplemvvm.utils.extensions.hideKeyboard
import org.imaginativeworld.simplemvvm.utils.extensions.indefiniteSnackbar
import org.imaginativeworld.simplemvvm.utils.extensions.longSnackbar
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    CommonFunctions,
    OnFragmentInteractionListener,
    MainActivityExtraOnFragmentInteractionListener {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharedPref: SharedPref

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

        initViews()
        initListeners()

        sharedPref.isUserLoggedIn()

        val v1 = Event("v1")
        val v2 = Event("v2")
        val v3 = Event("v3")
        val v4 = Event("v4")

        Timber.e("$v1, $v2, $v3, $v4")
    }

    // todo Add this to onResume
    private fun updateOneSignalId() {
        // this can also be used to observer:
        // https://documentation.onesignal.com/docs/android-native-sdk#addsubscriptionobserver
        if (OneSignal.getDeviceState() != null && OneSignal.getDeviceState()!!.isSubscribed) {
            val userId = OneSignal.getDeviceState()!!.userId

//            if (UserDataManager.isUserLoggedIn()
//                && (UserDataManager.getOneSignalPlayerId() == null || !UserDataManager.getOneSignalPlayerId()
//                    .equals(userId))
//            ) {
//                AuthRepo.get().updateOneSignalId(userId)
//            }
        }
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
        try {
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
        } catch (e: Exception) {
        }
    }

    override fun hideLoading() {
        try {
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
        } catch (e: Exception) {
        }
    }

    override fun getActivityViewModel(): MainViewModel {
        return viewModel
    }
}
