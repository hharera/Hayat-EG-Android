package com.harera.dwaa.data.repository

import com.harera.dwaa.data.service.UnitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UnitRepositoryImpl @Inject constructor(
    private val unitService: UnitService
) : UnitRepository {

    override fun getUnitList(): Flow<Any> = flow {
        unitService.getUnitList()
    }
}