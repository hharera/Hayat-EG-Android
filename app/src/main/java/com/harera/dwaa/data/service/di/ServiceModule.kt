package com.harera.dwaa.data.service.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.harera.dwaa.data.service.*
import com.harera.dwaa.utils.Request
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindDonationService(donationService: DonationServiceImpl): DonationService

    @Binds
    abstract fun bindMedicineService(medicineService: MedicineServiceImpl): MedicineService

    @Binds
    abstract fun bindUnitService(unitService: UnitServiceImpl): UnitService
}