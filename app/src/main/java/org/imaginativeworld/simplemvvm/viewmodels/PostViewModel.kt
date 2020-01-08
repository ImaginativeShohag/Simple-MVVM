package org.imaginativeworld.simplemvvm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.PostResponse
import org.imaginativeworld.simplemvvm.network.ApiException
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import org.imaginativeworld.simplemvvm.utils.Resource

class PostViewModel(
    private val repository: AppRepository
) : ViewModel() {

    fun clearPostObservables() {
        getPostsResponse.value = null
    }

    // ----------------------------------------------------------------

    val getPostsResponse: MutableLiveData<Resource<List<PostResponse>>?> by lazy {
        MutableLiveData<Resource<List<PostResponse>>?>()
    }

    fun getPosts() {

        viewModelScope.launch {

            try {

                val response = repository.getPosts()

                getPostsResponse.setValue(Resource.Success(response))

            } catch (e: ApiException) {

                getPostsResponse.setValue(Resource.Error(e.message!!))

            }
        }

    }

}