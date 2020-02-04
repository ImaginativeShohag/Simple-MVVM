package org.imaginativeworld.simplemvvm.interfaces

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import org.imaginativeworld.simplemvvm.views.fragments.user.UserViewModel

interface OnFragmentInteractionListener {

    fun setAppTitle(title: String)

    fun getAppViewModel(): UserViewModel?

    fun gotoFragment(@IdRes destinationResId: Int)

    fun gotoFragment(@IdRes destinationResId: Int, data: Bundle)

    fun gotoFragment(navDirections: NavDirections)

    fun goBack()

    fun showSnackbar(message: String)

    fun showSnackbar(message: String, buttonText: String, action: (View) -> Unit)

    fun showLoading()

    fun hideLoading()


}