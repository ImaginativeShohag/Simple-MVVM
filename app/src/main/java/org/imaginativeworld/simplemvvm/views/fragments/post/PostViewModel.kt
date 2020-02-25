package org.imaginativeworld.simplemvvm.views.fragments.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.PostResponse
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.AppRepository

class PostViewModel(
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

    private val _postItems: MutableLiveData<List<PostResponse>> by lazy {
        MutableLiveData<List<PostResponse>>()
    }

    val postItems: LiveData<List<PostResponse>?>
        get() = _postItems

    // ----------------------------------------------------------------

    fun clearPostObservables() {
        _eventShowMessage.value = null
        _eventShowLoading.value = null
        _postItems.value = null
    }

    // ----------------------------------------------------------------

    fun getPosts(
        format: String,
        accessToken: String
    ) = viewModelScope.launch {

        _eventShowLoading.value = true

        try {
            _postItems.value = repository.getPosts(
                format,
                accessToken
            )
        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false

    }


}