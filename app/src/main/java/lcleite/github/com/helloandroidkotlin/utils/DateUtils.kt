package lcleite.github.com.helloandroidkotlin.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateUtils {
    companion object {
        private val TWEET_DATE_FORMAT: String = "EEE MMM dd HH:mm:ss zzz yyyy"
        private val SEARCH_RESULT_DATE_FORMAT: String = "MMM dd yyyy HH:mm"

        fun createDateFromString(dateString: String): Date {
            var date: Date? = null
            val dateFormat: DateFormat = SimpleDateFormat(TWEET_DATE_FORMAT, Locale.getDefault())

            try {
                date = dateFormat.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return Date()
        }

        fun getStringFromDate(date: Date): String {
            val dateFormat: DateFormat = SimpleDateFormat(SEARCH_RESULT_DATE_FORMAT, Locale.getDefault())

            return dateFormat.format(date)
        }
    }
}
