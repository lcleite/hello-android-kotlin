package lcleite.github.com.helloandroidjava.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View
import android.view.inputmethod.InputMethodManager;
import lcleite.github.com.helloandroidkotlin.R

class AndroidUtils {
    companion object {
        fun hideSoftKeyboard (context: Context, view: View) {
            val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
        }

        fun getPreferences(context: Context): SharedPreferences {
            val preferencesKey: String = context.getString(R.string.preference_file_key)

            return context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE)
        }

        fun getMaxTweetsPreference(context: Context): Int{
            val preferenceKey: String = context.getString(R.string.preference_max_tweets)
            val sharedPrefs: SharedPreferences = getPreferences(context)

            return sharedPrefs.getInt(preferenceKey, 10)
        }
    }
}
