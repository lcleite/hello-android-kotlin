package lcleite.github.com.helloandroidkotlin.async

import android.graphics.Bitmap
import android.widget.ImageView
import lcleite.github.com.helloandroidkotlin.R
import lcleite.github.com.helloandroidkotlin.utils.CircularImage


class SetDownloadedImageTask(imageUrl: String, targetImageView: ImageView) : DownloadImageTask(imageUrl, null) {

    private var targetImageView: ImageView

    init {
        this.imageUrl = imageUrl
        this.targetImageView = targetImageView
    }

    override fun onPreExecute() {
        targetImageView.setImageResource(R.drawable.downloading_placeholder)
    }

    override fun doInBackground(vararg params: Void?): Bitmap? {
        val downloadedBitmap: Bitmap? = super.doInBackground()

        return transformBitmap(downloadedBitmap)
    }

    override fun onPostExecute(downloadedBitmap: Bitmap?) {
        downloadedBitmap?.let { bitmap ->
            targetImageView.setImageBitmap(bitmap)
        }
    }

    private fun transformBitmap(downloadedBitmap: Bitmap?): Bitmap? {
        downloadedBitmap?.let { bitmap ->
            val imageSize = 100
            return CircularImage.getRoundedCornerBitmap(bitmap, imageSize, imageSize)
        }

        return null
    }
}
