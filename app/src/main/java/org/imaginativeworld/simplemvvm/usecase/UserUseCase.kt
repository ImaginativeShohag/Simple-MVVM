package org.imaginativeworld.simplemvvm.usecase

import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import org.imaginativeworld.simplemvvm.models.User
import org.imaginativeworld.simplemvvm.repositories.AppRepository

object UserUseCase {
    fun getUser(repository: AppRepository): List<DemoUserEntity> {
        // ....
        return repository.getUsers()
    }
}