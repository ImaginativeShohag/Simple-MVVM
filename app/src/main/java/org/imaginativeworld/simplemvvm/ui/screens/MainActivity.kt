/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Simple MVVM
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.ui.screens

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.imaginativeworld.simplemvvm.BuildConfig
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.ActivityMainBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.MainActivityExtraOnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.models.Event
import org.imaginativeworld.simplemvvm.utils.EncryptionUtils
import org.imaginativeworld.simplemvvm.utils.SharedPref
import org.imaginativeworld.simplemvvm.utils.Utils.ignoreCrash
import org.imaginativeworld.simplemvvm.utils.extensions.hideKeyboard
import org.imaginativeworld.simplemvvm.utils.extensions.indefiniteSnackbar
import org.imaginativeworld.simplemvvm.utils.extensions.longSnackbar
import timber.log.Timber

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    CommonFunctions,
    OnFragmentInteractionListener,
    MainActivityExtraOnFragmentInteractionListener {

    // TODO: Remove `MainActivityExtraOnFragmentInteractionListener` by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen
    private lateinit var navController: NavController

    @Inject
    lateinit var sharedPref: SharedPref

    private val viewModel: MainViewModel by viewModels()

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initObservers()
        initViews()
        initListeners()
        splashScreenOnExitAnimation()
        updateOneSignalId()
        checkNotificationPermission()

        sharedPref.isUserLoggedIn()

        val v1 = Event("v1")
        val v2 = Event("v2")
        val v3 = Event("v3")
        val v4 = Event("v4")

        Timber.e("$v1, $v2, $v3, $v4")

        ignoreCrash {
            // Do things that may crash.
        }

        encryptionUtilsDemo()

        Timber.e("baseUrl: ${BuildConfig.BASE_URL}")
    }

    private fun splashScreenOnExitAnimation() {
        // Add a callback that's called when the splash screen is animating to the
        // app content.
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.view.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 1000L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.doOnEnd { splashScreenView.remove() }

            // Run your animation.
            slideUp.start()
        }
    }

    private fun encryptionUtilsDemo() {
        val encryptedText = EncryptionUtils.encrypt("Lorem Ipsum Dolor".toByteArray())

        Timber.e("Encrypted Text: ${String(encryptedText)}")

        val decryptedText = EncryptionUtils.decrypt(encryptedText)

        Timber.e("Decrypted Text: ${String(decryptedText)}")
    }

    // TODO: Add this to onResume
    private fun updateOneSignalId() {
        // this can also be used to observer:
        // https://documentation.onesignal.com/docs/android-native-sdk#addsubscriptionobserver
        if (OneSignal.getDeviceState() != null && OneSignal.getDeviceState()!!.isSubscribed) {
            val userId = OneSignal.getDeviceState()!!.userId

            Timber.d("OneSignal User Id: $userId")

//            if (UserDataManager.isUserLoggedIn()
//                && (UserDataManager.getOneSignalPlayerId() == null || !UserDataManager.getOneSignalPlayerId()
//                    .equals(userId))
//            ) {
//                AuthRepo.get().updateOneSignalId(userId)
//            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected, and what
                    // features are disabled if it's declined. In this UI, include a
                    // "cancel" or "no thanks" button that lets the user continue
                    // using your app without granting the permission.
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Notification permission")
                    builder.setMessage("Please grant notification permission to get notifications.")
                    builder.setPositiveButton("Ok") { _, _ ->
                        requestNotificationPermissionLauncher.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    }
                    builder.setNegativeButton("Cancel") { _, _ ->
                        /* no-op */
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestNotificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }
        }
    }

    override fun initViews() {
        /* no-op */
    }

    override fun initListeners() {
        /* no-op */
    }

    override fun initObservers() {
        /* no-op */
    }

    override fun setAppTitle(title: String) {
        setTitle(title)
    }

    override fun navigate(@IdRes destinationResId: Int) {
        hideKeyboard()

        if (navController.currentDestination == null) {
            navController.navigate(destinationResId)
        } else {
            navController.currentDestination?.let {
                if (it.id != destinationResId) {
                    navController.navigate(destinationResId)
                }
            }
        }
    }

    override fun navigate(@IdRes destinationResId: Int, data: Bundle) {
        hideKeyboard()

        if (navController.currentDestination == null) {
            navController.navigate(destinationResId, data)
        } else {
            navController.currentDestination?.let {
                if (it.id != destinationResId) {
                    navController.navigate(destinationResId, data)
                }
            }
        }
    }

    override fun navigate(navDirections: NavDirections) {
        hideKeyboard()

        navController.navigate(navDirections)
    }

    override fun goBack() {
        hideKeyboard()

        onBackPressedDispatcher.onBackPressed()
    }

    override fun showSnackbar(message: String) {
        binding.mainContainer.longSnackbar(message)
    }

    override fun showSnackbar(message: String, buttonText: String, action: (View) -> Unit) {
        binding.mainContainer.indefiniteSnackbar(message, buttonText, action)
    }

    override fun showLoading() {
        ignoreCrash {
            binding.loadingView.globalLoadingLayout.animate()
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
                .start()
        }
    }

    override fun hideLoading() {
        ignoreCrash {
            binding.loadingView.globalLoadingLayout.animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.loadingView.globalLoadingLayout.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animator) {}
                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationStart(animation: Animator) {}
                })
                .start()
        }
    }

    override fun getActivityViewModel(): MainViewModel {
        return viewModel
    }
}
