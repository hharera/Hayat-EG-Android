package com.harera.hayat.data.domain

import java.util.*

data class InsertDonationResponse(
    var postId: Int,
    var postDescription: String? = null,
    var postDate: String,
    var medicineId: Int,
    var medicineExpirationDate: Date,
    var cityId: Int,
    var uid: Int,
    var postStateId: Int,
)