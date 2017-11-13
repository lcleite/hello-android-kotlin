package lcleite.github.com.helloandroidkotlin.model

import android.os.Parcel
import android.os.Parcelable

class TweetMedia : Parcelable{
    enum class Type{
        NONE, PHOTO, VIDEO
    }

    lateinit var type: Type
    var photoUrl: String? = null
    var videoUrl: String? = null

   constructor() {}

    constructor(parcel: Parcel) {
        type = Type.valueOf(parcel.readString())
        photoUrl = parcel.readString()
        videoUrl = parcel.readString()
    }

    override fun toString(): String {
        return "TweetMedia{" +
                "type=" + type +
                ", photoUrl='" + photoUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}'
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type.name)
        parcel.writeString(photoUrl)
        parcel.writeString(videoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TweetMedia> {
        override fun createFromParcel(parcel: Parcel): TweetMedia {
            return TweetMedia(parcel)
        }

        override fun newArray(size: Int): Array<TweetMedia?> {
            return arrayOfNulls<TweetMedia>(size)
        }
    }
}
