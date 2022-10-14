package com.harera.dwaa.di

import com.harera.dwaa.network.firebase.FirebaseAuth
import com.harera.dwaa.network.repository.AuthManager
import com.harera.dwaa.data.repository.MedicineRepository
import com.harera.dwaa.data.repository.MedicineRepositoryImpl
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
    abstract fun bindMedicineRepository(medicineRepository: MedicineRepositoryImpl) : MedicineRepository
}