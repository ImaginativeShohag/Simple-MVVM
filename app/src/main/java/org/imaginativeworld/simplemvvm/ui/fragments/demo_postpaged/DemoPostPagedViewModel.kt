package org.imaginativeworld.simplemvvm.ui.fragments.demo_postpaged

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.models.DemoPostResult
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import javax.inject.Inject

class DemoPostPagedViewModel @Inject constructor(
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

    private val _postItems: MediatorLiveData<PagedList<DemoPostResult>> by lazy {
        MediatorLiveData<PagedList<DemoPostResult>>()
    }

    val postItems: LiveData<PagedList<DemoPostResult>?>
        get() = _postItems

    // ----------------------------------------------------------------

    fun getPostsPaged(
        format: String,
        accessToken: String,
        listener: OnDataSourceErrorListener
    ) {

        _eventShowLoading.value = true

        val result = repository.getPostsPaged(
            format,
            accessToken,
            listener
        )

        _postItems.addSource(result) {

            _postItems.value = it

            _eventShowLoading.value = false

        }

    }

}
