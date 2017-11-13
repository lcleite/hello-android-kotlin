package lcleite.github.com.helloandroidkotlin.async

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


open class DownloadImageTask(imageUrl: String, callback: Callback?) : AsyncTask<Void, Void, Bitmap?>() {

    private var callback: Callback?
    protected var imageUrl: String

    init {
        this.imageUrl = imageUrl
        this.callback = callback
    }

    override fun doInBackground(vararg params: Void?): Bitmap? {
        try {
            return getBitmapFromUrl()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onPostExecute(downloadedBitmap: Bitmap?) {
        downloadedBitmap?.let { bitmap ->
            callback?.onImageDownloadComplete(bitmap)
        }
    }

    private fun getBitmapFromUrl(): Bitmap? {
        val urlObject: URL = URL(imageUrl)
        val urlConnection: HttpURLConnection = urlObject.openConnection() as HttpURLConnection

        return BitmapFactory.decodeStream(urlConnection.inputStream)
    }

    interface Callback{
        fun onImageDownloadComplete(downloadedBitmap: Bitmap)
    }
}
