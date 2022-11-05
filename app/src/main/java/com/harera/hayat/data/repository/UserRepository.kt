package com.harera.hayat.data.repository

import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun getCurrentUser(): FirebaseUser?
}