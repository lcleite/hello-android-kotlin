package lcleite.github.com.helloandroidkotlin.mapper

import lcleite.github.com.helloandroidkotlin.model.Tweet
import lcleite.github.com.helloandroidkotlin.utils.DateUtils
import org.json.JSONObject;


class TweetJsonMapper() : JsonMapper<Tweet> {

    private var twitterUserJsonMapper: TwitterUserJsonMapper
    private var tweetContentJsonMapper: TweetContentJsonMapper
    private var tweetMediaJsonMapper: TweetMediaJsonMapper

    init {
        twitterUserJsonMapper = TwitterUserJsonMapper()
        tweetContentJsonMapper = TweetContentJsonMapper()
        tweetMediaJsonMapper = TweetMediaJsonMapper()
    }

    override fun toModel(jsonObject: JSONObject): Tweet {
        val tweet: Tweet = Tweet()

        tweet.createdAt = DateUtils.createDateFromString(jsonObject.optString("created_at"))
        tweet.user = twitterUserJsonMapper.toModel(jsonObject)
        tweet.content = tweetContentJsonMapper.toModel(jsonObject)
        tweet.media = tweetMediaJsonMapper.toModel(jsonObject)

        return tweet
    }


}
