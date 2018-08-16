package com.an.github.model

import android.os.Parcel
import android.os.Parcelable
import com.an.github.utils.AppUtils
import com.google.gson.annotations.SerializedName


data class Repository(
        val id: Int,
        @SerializedName("node_id") val nodeId: String,
        val name: String,
        @SerializedName("full_name") val fullName: String,
        val owner: Owner,
        val private: Boolean,
        @SerializedName("html_url") val htmlUrl: String,
        val description: String,
        val fork: Boolean,
        val url: String,
        @SerializedName("downloads_url") val downloadsUrl: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String,
        @SerializedName("pushed_at") val pushedAt: String,
        @SerializedName("git_url") val gitUrl: String,
        val size: Int,
        @SerializedName("stargazers_count") val stargazersCount: Int,
        val language: String,
        @SerializedName("has_issues") val hasIssues: Boolean,
        @SerializedName("has_downloads") val hasDownloads: Boolean,
        @SerializedName("forks_count") val forksCount: Int,
        val archived: Boolean,
        @SerializedName("open_issues_count") val openIssuesCount: Int,
        val forks: Int,
        @SerializedName("open_issues") val openIssues: Int,
        val watchers: Int,
        @SerializedName("default_branch") val defaultBranch: String,
        val score: Double) : Parcelable {


     val formattedDate: String
        get() =  AppUtils.getDate(createdAt)


    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Owner::class.java.classLoader),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nodeId)
        parcel.writeString(name)
        parcel.writeString(fullName)
        parcel.writeParcelable(owner, flags)
        parcel.writeByte(if (private) 1 else 0)
        parcel.writeString(htmlUrl)
        parcel.writeString((if (description == null) "" else description))
        parcel.writeByte(if (fork) 1 else 0)
        parcel.writeString(url)
        parcel.writeString(downloadsUrl)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeString(pushedAt)
        parcel.writeString(gitUrl)
        parcel.writeInt(size)
        parcel.writeInt(stargazersCount)
        parcel.writeString(language)
        parcel.writeByte(if (hasIssues) 1 else 0)
        parcel.writeByte(if (hasDownloads) 1 else 0)
        parcel.writeInt(forksCount)
        parcel.writeByte(if (archived) 1 else 0)
        parcel.writeInt(openIssuesCount)
        parcel.writeInt(forks)
        parcel.writeInt(openIssues)
        parcel.writeInt(watchers)
        parcel.writeString(defaultBranch)
        parcel.writeDouble(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repository> {
        override fun createFromParcel(parcel: Parcel): Repository {
            return Repository(parcel)
        }

        override fun newArray(size: Int): Array<Repository?> {
            return arrayOfNulls(size)
        }
    }


    private fun writeStringToParcel(p: Parcel, s: String?) {
        p.writeByte((if (s != null) 1 else 0).toByte())
        p.writeString(s)
    }

    private fun readStringFromParcel(p: Parcel): String? {
        val isPresent = p.readByte().toInt() == 1
        return if (isPresent) p.readString() else null
    }
}

