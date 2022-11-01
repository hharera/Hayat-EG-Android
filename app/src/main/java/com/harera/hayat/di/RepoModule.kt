package com.harera.hayat.di

import com.harera.hayat.network.firebase.FirebaseAuth
import com.harera.hayat.network.repository.AuthManager
import com.harera.hayat.data.repository.MedicineRepository
import com.harera.hayat.data.repository.MedicineRepositoryImpl
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