package com.harera.dwaa.data.service

import com.harera.dwaa.data.domain.InsertDonationResponse
import com.harera.dwaa.data.service.domain.GetDonationsResponse
import com.harera.dwaa.data.service.domain.PostMedicineRequest


interface DonationService {

    suspend fun insertDonationPost(token: String, postMedicineRequest: PostMedicineRequest) : InsertDonationResponse
    suspend fun getDonationPosts(token: String) : GetDonationsResponse
}