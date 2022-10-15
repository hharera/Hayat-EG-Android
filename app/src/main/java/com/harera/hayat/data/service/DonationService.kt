package com.harera.hayat.data.service

import com.harera.hayat.data.domain.InsertDonationResponse
import com.harera.hayat.data.service.domain.GetDonationsResponse
import com.harera.hayat.data.service.domain.PostMedicineRequest


interface DonationService {

    suspend fun insertDonationPost(token: String, postMedicineRequest: PostMedicineRequest) : InsertDonationResponse
    suspend fun getDonationPosts(token: String) : GetDonationsResponse
}