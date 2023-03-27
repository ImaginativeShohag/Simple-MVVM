package org.imaginativeworld.simplemvvm.ui.screens.awesometodos

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AwesomeTodosMainViewModel @Inject constructor() : ViewModel() {

    private val _navigate: MutableLiveData<Class<out Fragment>?> by lazy {
        MutableLiveData<Class<out Fragment>?>()
    }
    val navigate: LiveData<Class<out Fragment>?>
        get() = _navigate

    // ----------------------------------------------------------------

    fun navigate(fragmentClass: Class<out Fragment>) {
        _navigate.postValue(fragmentClass)
    }
}
