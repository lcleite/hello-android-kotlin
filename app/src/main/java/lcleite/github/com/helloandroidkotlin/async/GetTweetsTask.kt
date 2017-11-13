package lcleite.github.com.helloandroidkotlin.async

import android.content.Context
import android.os.AsyncTask
import lcleite.github.com.helloandroidjava.utils.AndroidUtils
import lcleite.github.com.helloandroidkotlin.mapper.TweetJsonMapper
import lcleite.github.com.helloandroidkotlin.model.Tweet

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class GetTweetsTask : AsyncTask<String, Void, String> {

    var callback: Callback
    var responseMaxLength: Int

    constructor(callback: Callback) {
        this.callback = callback
        this.responseMaxLength = AndroidUtils.getMaxTweetsPreference(callback as Context)
    }

    override fun doInBackground(vararg params: String?): String {
        val query: String = params[0]!!
        val url: String = "https://api.twitter.com/1.1/search/tweets.json?q=" + query
        var urlConnection: HttpURLConnection? = null
        var response: String = ""

        try {
            urlConnection = createHttpUrlConnection(url)
            response = getResponse(urlConnection)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect()
        }

        return response
    }

    override fun onPostExecute(response: String) {
        val results: MutableList<Tweet> = ArrayList<Tweet>()

        try {
            val responseJson: JSONObject = JSONObject(response)
            val jsonArray: JSONArray = responseJson.getJSONArray("statuses")

            for(i in 0 until jsonArray.length()){
                if(i == responseMaxLength)
                    break

                val jsonObject: JSONObject = jsonArray.get(i) as JSONObject
                val tweet: Tweet = TweetJsonMapper().toModel(jsonObject)
                results.add(tweet)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        callback.setSearchResult(results)
    }

    private fun createHttpUrlConnection(url: String): HttpURLConnection{
        val urlObject: URL = URL(url)
        val urlConnection: HttpURLConnection = urlObject.openConnection() as HttpURLConnection

        urlConnection.requestMethod = "GET"
        urlConnection.setRequestProperty("Authorization",
                "Bearer AAAAAAAAAAAAAAAAAAAAANKm2QAAAAAA4egRyibPOIGTFLzy%2BhDnYDdr62o%3DcGtYhty15tRTVLwWjWux3S2rcP4cLxGoFEgJcissPmYOYkSikA")

        return urlConnection
    }

    private fun getResponse(urlConnection: HttpURLConnection): String{
        val stream: InputStream?

        if (urlConnection.responseCode == HttpURLConnection.HTTP_OK)
            stream = BufferedInputStream(urlConnection.inputStream)
        else
            stream = BufferedInputStream(urlConnection.errorStream)

        return readStreamLines(stream)
    }

    private fun readStreamLines(stream: InputStream): String {
        val response: StringBuilder = StringBuilder()
        val reader: BufferedReader = BufferedReader(InputStreamReader(stream))

        reader.forEachLine { line ->
            response.append(line)
        }

        return response.toString()
    }

    interface Callback{
        fun setSearchResult(results: List<Tweet>)
    }
}
