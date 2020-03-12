package org.imaginativeworld.simplemvvm.views.fragments.postpaged

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagedList
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.models.PostResult
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import javax.inject.Inject

class PostPagedViewModel @Inject constructor(
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

    private val _postItems: MediatorLiveData<PagedList<PostResult>> by lazy {
        MediatorLiveData<PagedList<PostResult>>()
    }

    val postItems: LiveData<PagedList<PostResult>?>
        get() = _postItems

    // ----------------------------------------------------------------

    fun getPostsPaged(
        format: String,
        accessToken: String,
        listener: OnDataSourceErrorListener
    ) {

        val result = repository.getPostsPaged(
            format,
            accessToken,
            listener
        )

        _postItems.addSource(result, Observer {



        })

    }

}
