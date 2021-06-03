package com.whiteside.dwaa.ui.feed

import com.google.firebase.Timestamp

class FeedMedicine() {
    lateinit var name: String
    lateinit var id: String
    lateinit var uid: String
    lateinit var expireDate: Timestamp
    lateinit var addingTime: Timestamp
    var price: Float = 0f
    lateinit var location: Map<String, Double>
    lateinit var category: String
    lateinit var imageUrl: String
}
