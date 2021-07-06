package com.whiteside.dwaa.ui.saerchfilter

import com.google.firebase.firestore.Query

data class SearchSorting(
    var propertyName: String,
    var direction: Query.Direction
)
