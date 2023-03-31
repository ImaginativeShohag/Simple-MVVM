package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import org.imaginativeworld.simplemvvm.utils.SharedPref

@HiltViewModel
class TodoSplashViewModel @Inject constructor(
    private val repository: AppRepository,
    private val sharedPref: SharedPref
) : ViewModel() {

    private val _eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val eventShowMessage: LiveData<String?>
        get() = _eventShowMessage

    // ----------------------------------------------------------------

    private val _eventShowLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    val eventShowLoading: LiveData<Boolean?>
        get() = _eventShowLoading

    // ----------------------------------------------------------------

    private val _eventAuthSuccess: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>(null)
    }

    val eventAuthSuccess: LiveData<Boolean?>
        get() = _eventAuthSuccess

    // ----------------------------------------------------------------

    fun checkAuthentication() = viewModelScope.launch {
        val existingUser = sharedPref.getUser()

        if (existingUser == null) {
            _eventAuthSuccess.postValue(false)
        } else {
            _eventAuthSuccess.postValue(true)
        }
    }
}
