package org.imaginativeworld.simplemvvm.views.fragments.demo_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.PostResult
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import javax.inject.Inject

class DemoPostViewModel @Inject constructor(
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

    private val _postItems: MutableLiveData<List<PostResult>> by lazy {
        MutableLiveData<List<PostResult>>()
    }

    val postItems: LiveData<List<PostResult>?>
        get() = _postItems

    // ----------------------------------------------------------------

    fun getPosts(
        format: String,
        accessToken: String
    ) = viewModelScope.launch {

        _eventShowLoading.value = true

        try {
            val postResponse = repository.getPosts(
                format,
                accessToken
            )

            if (postResponse._meta.success) {

                _postItems.value = postResponse.result

            } else {

                throw ApiException("Code: ${postResponse._meta.code} & Message: ${postResponse._meta.message}")

            }

        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false

    }


}