package lcleite.github.com.helloandroidkotlin.utils

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class StringUtils {
    companion object {
        fun encodeSearchTerm(query: String): String{
            try {
                return URLEncoder.encode(query, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return query
        }

        fun screenNameWithAt(screenName: String?): String{
            screenName?.let{ name ->
                return "@" + name
            }
            return ""
        }
    }
}
