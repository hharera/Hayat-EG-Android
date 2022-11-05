package com.harera.hayat.data.service

import com.google.firebase.auth.FirebaseUser

interface UserService {

    fun getCurrentUser(): FirebaseUser?
}