package lcleite.github.com.helloandroidkotlin.model

import android.os.Parcel
import android.os.Parcelable

class TwitterUser : Parcelable{
    var userName: String? = null
    var userScreenName: String? = null
    var userProfileImageUrl: String? = null

    constructor() {}

    protected constructor(parcel: Parcel) {
        userName = parcel.readString()
        userScreenName = parcel.readString()
        userProfileImageUrl = parcel.readString()
    }

    override fun toString(): String {
        return "TwitterUser{" +
                "userName='" + userName + '\'' +
                ", userScreenName='" + userScreenName + '\'' +
                ", userProfileImageUrl='" + userProfileImageUrl + '\'' +
                '}'
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(userScreenName)
        parcel.writeString(userProfileImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TwitterUser> {
        override fun createFromParcel(parcel: Parcel): TwitterUser {
            return TwitterUser(parcel)
        }

        override fun newArray(size: Int): Array<TwitterUser?> {
            return arrayOfNulls<TwitterUser>(size)
        }
    }
}
