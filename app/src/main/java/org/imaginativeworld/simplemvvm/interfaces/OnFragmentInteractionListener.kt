package org.imaginativeworld.simplemvvm.interfaces

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavDirections

interface OnFragmentInteractionListener {

    fun setAppTitle(title: String)

    fun navigate(@IdRes destinationResId: Int)

    fun navigate(@IdRes destinationResId: Int, data: Bundle)

    fun navigate(navDirections: NavDirections)

    fun goBack()

    fun showSnackbar(message: String)

    fun showSnackbar(message: String, buttonText: String, action: (View) -> Unit)

    fun showLoading()

    fun hideLoading()
}
