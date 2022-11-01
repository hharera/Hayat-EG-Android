package com.harera.hayat.data.service

import com.harera.hayat.data.service.domain.Unit


interface UnitService {

    suspend fun getUnitList() : List<Unit>
}