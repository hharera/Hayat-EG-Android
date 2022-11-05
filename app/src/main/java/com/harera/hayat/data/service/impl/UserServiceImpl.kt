package com.harera.hayat.data.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.harera.hayat.data.service.UserService
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : UserService {

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}