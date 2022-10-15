package com.harera.hayat.ui.saerchfilter

import com.google.firebase.firestore.Query

data class SearchSorting(
    var propertyName: String,
    var direction: Query.Direction
)
