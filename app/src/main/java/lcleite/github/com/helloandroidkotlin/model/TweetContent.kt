package lcleite.github.com.helloandroidkotlin.model

import android.os.Parcel
import android.os.Parcelable

class TweetContent : Parcelable{

    var text: String = ""
    var links: MutableList<String> = ArrayList<String>()
    var hashtags: MutableList<String> = ArrayList<String>()
    var userMentions: MutableList<String> = ArrayList<String>()

    constructor() {}

    constructor(parcel: Parcel) {
        text = parcel.readString()
        links = parcel.createStringArrayList()
        hashtags = parcel.createStringArrayList()
        userMentions = parcel.createStringArrayList()
    }

    override fun toString(): String {
        return "TweetContent{" +
                "text='" + text + '\'' +
                ", links=" + links +
                ", hashtags=" + hashtags +
                ", userMentions=" + userMentions +
                '}'
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeStringList(links)
        parcel.writeStringList(hashtags)
        parcel.writeStringList(userMentions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TweetContent> {
        override fun createFromParcel(parcel: Parcel): TweetContent {
            return TweetContent(parcel)
        }

        override fun newArray(size: Int): Array<TweetContent?> {
            return arrayOfNulls<TweetContent>(size)
        }
    }
}
