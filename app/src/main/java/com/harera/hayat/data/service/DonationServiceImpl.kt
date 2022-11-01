package com.harera.hayat.data.service

import com.harera.hayat.data.domain.InsertDonationResponse
import com.harera.hayat.data.service.domain.GetDonationsResponse
import com.harera.hayat.data.service.domain.PostMedicineRequest
import com.harera.hayat.data.service.utils.Request
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject


class DonationServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : DonationService {

    override suspend fun insertDonationPost(token: String, postMedicineRequest: PostMedicineRequest): InsertDonationResponse {
        return httpClient.post(Request.POST_MEDICINE_DONATION) {
            body = postMedicineRequest
            header("Authorization", "Bearer $token")
        }
    }

    override suspend fun getDonationPosts(token: String): GetDonationsResponse {
        return httpClient.get(Request.GET_DONATION_POSTS) {
            header("Authorization", "Bearer $token")
        }
    }
}