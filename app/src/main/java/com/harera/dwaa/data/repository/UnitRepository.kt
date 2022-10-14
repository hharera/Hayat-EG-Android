package com.harera.dwaa.data.repository

import com.harera.dwaa.data.domain.Unit
import kotlinx.coroutines.flow.Flow

interface UnitRepository {

    fun getUnitList(): Flow<Any>
}