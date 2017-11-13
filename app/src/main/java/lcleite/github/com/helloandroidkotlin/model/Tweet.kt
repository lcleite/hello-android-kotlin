package lcleite.github.com.helloandroidkotlin.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date


class Tweet : Parcelable{
    lateinit var user: TwitterUser
    lateinit var content: TweetContent
    lateinit var media: TweetMedia
    lateinit var createdAt: Date

    constructor(){}

    constructor(parcel: Parcel) {
        user = parcel.readParcelable(TwitterUser::class.java.classLoader)
        content = parcel.readParcelable(TweetContent::class.java.classLoader)
        media = parcel.readParcelable(TweetMedia::class.java.classLoader)
        createdAt = Date(parcel.readLong())
    }

    override fun toString(): String {
        return "Tweet{" +
                "user=" + user +
                ", content=" + content +
                ", media=" + media +
                ", createdAt=" + createdAt +
                '}'
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(content, flags)
        parcel.writeParcelable(media, flags)
        parcel.writeLong(createdAt.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tweet> {
        override fun createFromParcel(parcel: Parcel): Tweet {
            return Tweet(parcel)
        }

        override fun newArray(size: Int): Array<Tweet?> {
            return arrayOfNulls<Tweet>(size)
        }
    }
}
