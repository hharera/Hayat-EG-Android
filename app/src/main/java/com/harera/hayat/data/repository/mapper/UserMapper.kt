package com.harera.hayat.data.repository.mapper

import com.google.firebase.auth.FirebaseUser

object UserMapper {

    fun map(user: FirebaseUser) = UserModel(
        uid = user.uid,
        name = user.displayName,
        email = user.email,
        phoneNumber = user.phoneNumber,
        photoUrl = user.photoUrl.toString(),
        isEmailVerified = user.isEmailVerified,
        creationTimestamp = user.metadata.creationTimestamp,
        lastSignInTimestamp = user.metadata.lastSignInTimestamp
    )
}