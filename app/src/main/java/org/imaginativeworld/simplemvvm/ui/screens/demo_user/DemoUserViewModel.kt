package org.imaginativeworld.simplemvvm.ui.screens.demo_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import org.imaginativeworld.simplemvvm.usecase.UserUseCase
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DemoUserViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val totalData = 4

    private val names = listOf(
        "Shohag",
        "Rakib",
        "Shafee",
        "Imran"
    )

    private val phones = listOf(
        "01111111111",
        "02222222222",
        "03333333333",
        "04444444444"
    )

    private val images = listOf(
        "http://teamshunno.com/wp-content/uploads/2018/07/Sohag-278x300.jpg",
        "http://teamshunno.com/wp-content/uploads/2018/07/Rakib-1-283x300.jpg",
        "http://teamshunno.com/wp-content/uploads/2018/07/Shafee_new-3-300x300.png",
        "http://teamshunno.com/wp-content/uploads/2018/07/imran-1-285x300.jpg"
    )

    // ----------------------------------------------------------------

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

    fun clearUserObservables() {
        _userItems.value = null
    }

    // ----------------------------------------------------------------

    fun addUser() {
        viewModelScope.launch {
            // Create new user
            _eventShowLoading.value = true

            val randomIndex = Random.nextInt(totalData)

            val insertUserId = userUseCase.addUser(
                DemoUserEntity(
                    name = names[randomIndex],
                    phone = phones[randomIndex],
                    image = images[randomIndex]
                )
            )

            Timber.e("insertUserId: $insertUserId")

            _eventShowLoading.value = false

            // Finally reload data
            getUsers()
        }
    }

    // ----------------------------------------------------------------

    fun removeAllUsers() {
        viewModelScope.launch {
            userUseCase.removeAllUsers()

            getUsers()
        }
    }

    // ----------------------------------------------------------------

    private val _userItems: MutableLiveData<List<DemoUserEntity>> by lazy {
        MutableLiveData<List<DemoUserEntity>>()
    }

    val userItems: LiveData<List<DemoUserEntity>>
        get() = _userItems

    // ----------------------------------------------------------------


    fun getUsers() = viewModelScope.launch {
        _eventShowLoading.value = true

        _userItems.value = userUseCase.getUsers()

        _eventShowLoading.value = false
    }

    fun addToFav(user: DemoUserEntity, result: (isSuccess: Boolean) -> Unit) =
        viewModelScope.launch {
            userUseCase.updateUser(user)

            result(true)
        }

    fun removeFromFav(user: DemoUserEntity, result: (isSuccess: Boolean) -> Unit) =
        viewModelScope.launch {
            userUseCase.updateUser(user)

            result(true)
        }

}