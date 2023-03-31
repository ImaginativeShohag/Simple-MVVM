package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import org.imaginativeworld.simplemvvm.utils.SharedPref

@HiltViewModel
class TodoListViewModel @Inject constructor(
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

    private val _todoItems: MutableLiveData<List<TodoItem>> by lazy {
        MutableLiveData<List<TodoItem>>()
    }

    val todoItems: LiveData<List<TodoItem>?>
        get() = _todoItems

    // ----------------------------------------------------------------

    private val _eventSignOutSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val eventSignOutSuccess: LiveData<Boolean>
        get() = _eventSignOutSuccess

    // ----------------------------------------------------------------

    fun getTodos() = viewModelScope.launch {
        _eventShowLoading.value = true

        val user = sharedPref.getUser() ?: return@launch

        try {
            val response = repository.getTodos(user.id)

            _todoItems.value = response
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }

    fun signOut() = viewModelScope.launch {
        repository.signOut()
        sharedPref.reset()

        _eventSignOutSuccess.postValue(true)
    }
}
