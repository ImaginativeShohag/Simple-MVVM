package org.imaginativeworld.simplemvvm.ui.fragments.demo_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.DemoPostResult
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
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

    private val _postItems: MutableLiveData<List<DemoPostResult>> by lazy {
        MutableLiveData<List<DemoPostResult>>()
    }

    val postItems: LiveData<List<DemoPostResult>?>
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

            if (postResponse.code == HttpURLConnection.HTTP_OK) {

                _postItems.value = postResponse.result

            } else {

                throw ApiException("Code: ${postResponse.code}")

            }

        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false

    }


}