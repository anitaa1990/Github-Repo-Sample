package com.an.github.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ApiResponse(
        @SerializedName("total_count") val totalCount: Long,
        @SerializedName("incomplete_results") val incompleteResults: Boolean,
        val items: List<Repository> ) : Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readByte() != 0.toByte(),
            parcel.createTypedArrayList(Repository)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(totalCount)
        parcel.writeByte(if (incompleteResults) 1 else 0)
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApiResponse> {
        override fun createFromParcel(parcel: Parcel): ApiResponse {
            return ApiResponse(parcel)
        }

        override fun newArray(size: Int): Array<ApiResponse?> {
            return arrayOfNulls(size)
        }
    }
}