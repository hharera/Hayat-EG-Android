package com.whiteside.dwaa.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    companion object {
        @Provides
        @Singleton
        fun provideFirestoreInstance(): FirebaseFirestore = FirebaseFirestore.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseStorageInstance(): FirebaseStorage =  FirebaseStorage.getInstance()
    }
}