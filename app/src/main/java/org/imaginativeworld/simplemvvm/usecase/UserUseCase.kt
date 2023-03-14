package org.imaginativeworld.simplemvvm.usecase

import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend fun addUser(userModel: DemoUserEntity) {
        repository.addUser(userModel)
    }

    suspend fun removeAllUsers() {
        repository.removeAllUsers()
    }

    suspend fun getUsers(): List<DemoUserEntity> {
        return repository.getUsers()
    }

    suspend fun updateUser(user: DemoUserEntity) {
        return repository.updateUser(user)
    }
}