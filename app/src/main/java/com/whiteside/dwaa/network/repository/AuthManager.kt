package com.whiteside.dwaa.network.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential

interface AuthManager {

    fun login(credecitial: AuthCredential): Task<AuthResult>
    fun createCredential(verificationId: String, code: String): PhoneAuthCredential
    fun getCurrentUser(): FirebaseUser?
}