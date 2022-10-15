package com.harera.hayat.ui.saerchfilter

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.harera.hayat.ui.saerchfilter.model.PriceFilter

data class SearchFilters(
    var priceFilter: PriceFilter?,
    var addingTime: Timestamp?,
    var expireDate: Timestamp?,
    var category: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(PriceFilter::class.java.classLoader),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readString()
    ) {
        
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(priceFilter, flags)
        parcel.writeParcelable(addingTime, flags)
        parcel.writeParcelable(expireDate, flags)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchFilters> {
        override fun createFromParcel(parcel: Parcel): SearchFilters {
            return SearchFilters(parcel)
        }

        override fun newArray(size: Int): Array<SearchFilters?> {
            return arrayOfNulls(size)
        }
    }
}