package com.harera.hayat.data.repository.impl

import com.harera.hayat.data.repository.UserRepository
import com.harera.hayat.data.service.UserService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
) : UserRepository {


    override fun getCurrentUser() = flow {
        userService.getCurrentUser()
    }
}