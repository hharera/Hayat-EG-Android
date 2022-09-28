package com.whiteside.dwaa.ui.saerchfilter.model

import android.os.Parcel
import android.os.Parcelable

data class PriceFilter (
    val startPrice : Int,
    val endPrice : Int,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    ) {

    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<PriceFilter> {
        override fun createFromParcel(parcel: Parcel): PriceFilter {
            return PriceFilter(parcel)
        }

        override fun newArray(size: Int): Array<PriceFilter?> {
            return arrayOfNulls(size)
        }
    }
}