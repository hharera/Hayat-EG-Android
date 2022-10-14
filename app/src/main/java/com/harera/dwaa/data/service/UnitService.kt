package com.harera.dwaa.data.service

import com.harera.dwaa.data.service.domain.Unit


interface UnitService {

    suspend fun getUnitList() : List<Unit>
}