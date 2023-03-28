package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.details

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

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(
    private val repository: AppRepository
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

    private val _todo: MutableLiveData<TodoItem?> by lazy {
        MutableLiveData<TodoItem?>()
    }

    val todo: LiveData<TodoItem?>
        get() = _todo

    // ----------------------------------------------------------------

    fun getDetails(todoId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val todo = repository.getTodoDetails(todoId)

            _todo.postValue(todo)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
