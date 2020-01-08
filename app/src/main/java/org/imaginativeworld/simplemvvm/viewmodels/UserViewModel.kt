package org.imaginativeworld.simplemvvm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.UserEntity
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import org.imaginativeworld.simplemvvm.utils.Resource

class UserViewModel(
    private val repository: AppRepository
) : ViewModel() {

    fun clearUserObservables() {
        addUserResponse.value = null
        getUsersResponse.value = null
    }

    // ----------------------------------------------------------------

    val addUserResponse: MutableLiveData<Resource<Long>?> by lazy {
        MutableLiveData<Resource<Long>?>()
    }

    fun addUser(
        userModel: UserEntity
    ) {

        viewModelScope.launch {

            val user = repository.addUser(userModel)

            addUserResponse.setValue(Resource.Success(user))

        }

    }

    // ----------------------------------------------------------------

    val getUsersResponse: MutableLiveData<Resource<List<UserEntity>>?> by lazy {
        MutableLiveData<Resource<List<UserEntity>>?>()
    }

    fun getUsers() {

        viewModelScope.launch {

            val user = repository.getUsers()

            getUsersResponse.setValue(Resource.Success(user))

        }

    }


}