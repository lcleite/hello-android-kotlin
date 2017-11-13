package lcleite.github.com.helloandroidkotlin.mapper

import android.util.Log
import lcleite.github.com.helloandroidkotlin.model.TweetMedia
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class TweetMediaJsonMapper : JsonMapper<TweetMedia> {

    override fun toModel(jsonObject: JSONObject): TweetMedia {
        val tweetMedia: TweetMedia = TweetMedia()
        val entities: JSONObject = jsonObject.optJSONObject("entities")
        val media: JSONObject? = getMedia(entities)
        val extendedEntities: JSONObject? = jsonObject.optJSONObject("extended_entities")
        val extendedMedia: JSONObject? = getMedia(extendedEntities)

        if(media == null)
            setTweetWithNoMedia(tweetMedia)
        else if(extendedMedia == null)
            setTweetWithPhotoMedia(tweetMedia, media)
        else
            setTweetWithVideoMedia(tweetMedia, media, extendedMedia)

        return tweetMedia
    }

    private fun setTweetWithNoMedia(tweetMedia: TweetMedia){
        tweetMedia.type = TweetMedia.Type.NONE
    }

    private fun setTweetWithPhotoMedia(tweetMedia: TweetMedia, media: JSONObject) {
        tweetMedia.type = TweetMedia.Type.PHOTO
        tweetMedia.photoUrl = getPhotoUrl(media)
    }

    private fun setTweetWithVideoMedia(tweetMedia: TweetMedia, media: JSONObject, extendedMedia: JSONObject) {
        tweetMedia.type = getExtendedMediaType(extendedMedia)
        tweetMedia.photoUrl = getPhotoUrl(media)
        tweetMedia.videoUrl = getVideoUrl(extendedMedia)
    }

    private fun getMedia(entities: JSONObject?): JSONObject? {
        val media: JSONArray? = entities?.optJSONArray("media")

        media?.let {
            if (it.length() > 0)
                return it.get(0) as JSONObject
        }

        Log.d("TweetMediaJsonMapper", "Tweet without media")

        return null
    }

    private fun getExtendedMediaType(extendedMedia: JSONObject): TweetMedia.Type {
        val type: String = extendedMedia.optString("type")

        if(type == "photo")
            return TweetMedia.Type.PHOTO
        else
            return TweetMedia.Type.VIDEO
    }

    private fun getPhotoUrl(media: JSONObject): String{
        return getUrl(media, "media_url")
    }

    private fun getVideoUrl(media: JSONObject): String{
        return getUrl(media, "expanded_url")
    }

    private fun getUrl(media: JSONObject?, field: String ): String {
        media?.let{
            return it.optString(field)
        }

        return ""
    }
}
