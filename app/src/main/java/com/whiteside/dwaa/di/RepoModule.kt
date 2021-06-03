package com.whiteside.dwaa.di

import com.whiteside.dwaa.network.firebase.FirebaseAuth
import com.whiteside.dwaa.network.firebase.FirebaseMedicineRepository
import com.whiteside.dwaa.network.repository.AuthManager
import com.whiteside.dwaa.network.repository.MedicineRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindAuthManager(authManager: FirebaseAuth) : AuthManager

    @Binds
    abstract fun bindMedicineRepository(medicineRepository: FirebaseMedicineRepository) : MedicineRepository
}