package org.imaginativeworld.simplemvvm.ui.screens.awesometodos

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AwesomeTodosMainViewModel @Inject constructor() : ViewModel() {

    private val _navigate: MutableLiveData<NavDestination?> by lazy {
        MutableLiveData<NavDestination?>()
    }
    val navigate: LiveData<NavDestination?>
        get() = _navigate

    // ----------------------------------------------------------------

    private val _isLoadingVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isLoadingVisible: LiveData<Boolean>
        get() = _isLoadingVisible

    // ----------------------------------------------------------------

    fun navigate(destination: NavDestination) {
        _navigate.postValue(destination)
    }

    // ----------------------------------------------------------------

    fun showLoading() {
        _isLoadingVisible.postValue(true)
    }

    fun hideLoading() {
        _isLoadingVisible.postValue(false)
    }
}

data class NavDestination(
    val fragmentClass: Class<out Fragment>,
    val args: Bundle? = null,
    val addToBackStack: Boolean = true
)
