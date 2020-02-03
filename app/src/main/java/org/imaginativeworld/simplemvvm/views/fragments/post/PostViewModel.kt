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

    val eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val eventShowLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    // ----------------------------------------------------------------

    fun clearPostObservables() {
        eventShowLoading.value = null
        eventShowMessage.value = null
        _postsResponse.value = null
    }

    // ----------------------------------------------------------------

    private val _postsResponse: MutableLiveData<List<PostResponse>> by lazy {
        MutableLiveData<List<PostResponse>>()
    }

    val postsResponse: LiveData<List<PostResponse>?>
        get() = _postsResponse

    fun getPosts() = viewModelScope.launch {

        eventShowLoading.value = true

        try {
            _postsResponse.value = repository.getPosts()
        } catch (e: ApiException) {
            eventShowMessage.value = e.message
        }

        eventShowLoading.value = false

    }


}