package com.harera.hayat.ui.search

import android.os.Parcel
import android.os.Parcelable

data class SearchFilter(val propertyName: String, val value: Any) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(propertyName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchFilter> {
        override fun createFromParcel(parcel: Parcel): SearchFilter {
            return SearchFilter(parcel)
        }

        override fun newArray(size: Int): Array<SearchFilter?> {
            return arrayOfNulls(size)
        }
    }
}