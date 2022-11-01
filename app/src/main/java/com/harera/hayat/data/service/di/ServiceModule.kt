package com.harera.hayat.data.service.di

import com.harera.hayat.data.service.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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