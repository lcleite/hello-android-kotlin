package lcleite.github.com.helloandroidkotlin.mapper

import lcleite.github.com.helloandroidkotlin.model.TwitterUser
import org.json.JSONObject

class TwitterUserJsonMapper : JsonMapper<TwitterUser> {

    override fun toModel(jsonObject: JSONObject): TwitterUser {
        val twitterUser: TwitterUser = TwitterUser()
        val user: JSONObject = jsonObject.optJSONObject("user")

        twitterUser.userName = user.optString("name")
        twitterUser.userScreenName = user.optString("screen_name")
        twitterUser.userProfileImageUrl = user.optString("profile_image_url")

        return twitterUser
    }
}