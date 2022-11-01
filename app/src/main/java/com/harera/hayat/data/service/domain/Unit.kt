package com.harera.hayat.data.service.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Unit(
    @SerialName("")
    val unitName: String,
    val unitId: String
)
