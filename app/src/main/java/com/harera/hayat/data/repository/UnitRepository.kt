package com.harera.hayat.data.repository

import kotlinx.coroutines.flow.Flow

interface UnitRepository {

    fun getUnitList(): Flow<Any>
}