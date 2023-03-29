package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoItem
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.AppRepository

@HiltViewModel
class TodoAddViewModel @Inject constructor(
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

    private val _eventSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val eventSuccess: LiveData<Boolean>
        get() = _eventSuccess

    // ----------------------------------------------------------------

    fun isValid(
        title: String,
        dueDate: Date?,
        status: String
    ): Boolean {
        if (title.isBlank()) {
            _eventShowMessage.postValue("Please enter title!")
            return false
        }

        if (status.isBlank()) {
            _eventShowMessage.postValue("Please select status!")
            return false
        }

        if (dueDate == null) {
            _eventShowMessage.postValue("Please select due date!")
            return false
        }

        return true
    }

    fun add(
        title: String,
        dueDate: Date,
        status: String
    ) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            repository.addTodo(
                TodoItem(
                    title = title,
                    dueOn = dueDate,
                    status = status.lowercase()
                )
            )

            _eventSuccess.postValue(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false
    }
}
