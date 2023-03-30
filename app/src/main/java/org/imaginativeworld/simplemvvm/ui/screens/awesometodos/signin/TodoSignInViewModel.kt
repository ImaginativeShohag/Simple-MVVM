package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.awesometodos.User
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import org.imaginativeworld.simplemvvm.utils.SharedPref
import org.imaginativeworld.simplemvvm.utils.extensions.isValidEmail

@HiltViewModel
class TodoSignInViewModel @Inject constructor(
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

    private val _eventSignInSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventSignInSuccess: LiveData<Boolean>
        get() = _eventSignInSuccess

    // ----------------------------------------------------------------

    private fun isValid(
        name: String,
        email: String,
        gender: String
    ): Boolean {
        if (name.isBlank()) {
            _eventShowMessage.postValue("Please enter your name!")
            return false
        }

        if (email.isBlank()) {
            _eventShowMessage.postValue("Please enter your email!")
            return false
        }

        if (!email.isValidEmail()) {
            _eventShowMessage.postValue("Please enter a valid email!")
            return false
        }

        if (gender.isBlank()) {
            _eventShowMessage.postValue("Please select your gender!")
            return false
        }

        return true
    }

    fun signIn(
        name: String,
        email: String,
        gender: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, gender)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            val newUser = repository.signIn(
                User(
                    0, // It will be ignored in the server.
                    name,
                    email,
                    gender,
                    "active"
                )
            )

            if (newUser != null) {
                sharedPref.setUser(newUser)

                _eventSignInSuccess.postValue(true)
            } else {
                _eventShowMessage.postValue("Sign in failed!")
            }
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}