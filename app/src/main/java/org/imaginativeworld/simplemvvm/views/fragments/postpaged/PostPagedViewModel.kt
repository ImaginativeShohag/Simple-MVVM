package org.imaginativeworld.simplemvvm.views.fragments.postpaged

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
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

    private val _postItems: MutableLiveData<List<PostResult>> by lazy {
        MutableLiveData<List<PostResult>>()
    }

    val postItems: LiveData<List<PostResult>?>
        get() = _postItems

    // ----------------------------------------------------------------

    fun getPostsPaged(
        format: String,
        accessToken: String,
        listener: OnDataSourceErrorListener
    ): LiveData<PagedList<PostResult>> {

        return repository.getPostsPaged(
            format,
            accessToken,
            listener
        )

    }

}
