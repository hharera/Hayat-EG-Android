package com.harera.hayat.data.service

import com.harera.hayat.data.service.domain.Unit
import com.harera.hayat.data.service.utils.Request
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject


class UnitServiceImpl @Inject constructor(
    private val client: HttpClient,
) : UnitService {

    override suspend fun getUnitList(): List<Unit> {
        return client.get(Request.GET_UNIT_LIST)
    }
}