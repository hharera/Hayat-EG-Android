package com.harera.hayat.network.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import com.harera.hayat.network.repository.AuthManager
import javax.inject.Inject

class FirebaseAuth @Inject constructor() : AuthManager {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun login(credecitial: AuthCredential) =
        auth.signInWithCredential(credecitial)

    override fun createCredential(verificationId : String, code: String) =
        PhoneAuthProvider.getCredential(verificationId, code)

    override fun getCurrentUser(): FirebaseUser? =
        auth.currentUser
}