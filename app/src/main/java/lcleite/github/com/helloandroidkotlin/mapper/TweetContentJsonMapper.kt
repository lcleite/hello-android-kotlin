package lcleite.github.com.helloandroidkotlin.mapper

import lcleite.github.com.helloandroidkotlin.model.TweetContent
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class TweetContentJsonMapper : JsonMapper<TweetContent>{

    override fun toModel(jsonObject: JSONObject): TweetContent {
        val tweetContent: TweetContent = TweetContent()
        val entities: JSONObject = jsonObject.optJSONObject("entities")

        tweetContent.text = jsonObject.optString("text")
        tweetContent.links = getContent(entities.optJSONArray("urls"), "expanded_url")
        tweetContent.userMentions = getContent(entities.optJSONArray("user_mentions"), "screen_name")
        tweetContent.hashtags = getContent(entities.optJSONArray("hashtags"), "text")

        return tweetContent
    }

    private fun getContent(array: JSONArray, jsonField: String): MutableList<String> {
        val links: MutableList<String> = ArrayList<String>()

        try {
            for(i in 0 until array.length()){
                val jsonUrl: JSONObject = array.get(i) as JSONObject
                links.add(jsonUrl.optString(jsonField))
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }

        return links
    }
}
