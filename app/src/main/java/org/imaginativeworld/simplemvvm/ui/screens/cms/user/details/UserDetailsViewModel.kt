package org.imaginativeworld.simplemvvm.ui.screens.cms.user.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.User
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.UserRepository

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val eventShowMessage: LiveData<String?>
        get() = _eventShowMessage

    // ----------------------------------------------------------------

    private val _eventShowLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventShowLoading: LiveData<Boolean>
        get() = _eventShowLoading

    // ----------------------------------------------------------------

    private val _todo: MutableLiveData<User?> by lazy {
        MutableLiveData<User?>()
    }

    val todo: LiveData<User?>
        get() = _todo

    // ----------------------------------------------------------------

    private val _eventDeleteSuccess: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    val eventDeleteSuccess: LiveData<Boolean?>
        get() = _eventDeleteSuccess

    // ----------------------------------------------------------------

    fun getDetails(
        userId: Int
    ) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val user = repository.getUser(userId)

            _todo.postValue(user)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }

    fun delete(userId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            repository.deleteUser(userId)

            _eventDeleteSuccess.postValue(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
