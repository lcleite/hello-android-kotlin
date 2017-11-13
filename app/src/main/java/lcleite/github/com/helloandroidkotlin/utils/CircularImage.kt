package lcleite.github.com.helloandroidkotlin.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF

class CircularImage {
    companion object {
        fun getRoundedCornerBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
            val cropped: Bitmap = scaleCenterCrop(bitmap, newWidth, newHeight)
            val output: Bitmap = Bitmap.createBitmap(cropped.width, cropped.height, Bitmap.Config.ARGB_8888)
            val canvas: Canvas = Canvas(output)

            val color: Long = 0xff424242
            val paint: Paint = Paint()
            val rect: Rect = Rect(0, 0, cropped.width, cropped.height)
            val rectF: RectF = RectF(rect)
            val roundPx: Float = 1000f

            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color.toInt()
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(cropped, rect, rect, paint)

            return output
        }

        private fun scaleCenterCrop(source: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
            val sourceWidth: Int = source.width
            val sourceHeight: Int = source.height

            // Compute the scaling factors to fit the new height and width, respectively.
            // To cover the final image, the final scaling will be the bigger
            // of these two.
            val xScale: Float = newWidth.toFloat() / sourceWidth.toFloat()
            val yScale: Float = newHeight.toFloat() / sourceHeight.toFloat()
            val scale: Float = Math.max(xScale, yScale)

            // Now get the size of the source bitmap when scaled
            val scaledWidth: Float = scale * sourceWidth
            val scaledHeight: Float = scale * sourceHeight

            // Let's find out the upper left coordinates if the scaled bitmap
            // should be centered in the new size give by the parameters
            val left: Float = (newWidth - scaledWidth) / 2
            val top: Float = (newHeight - scaledHeight) / 2

            // The target rectangle for the new, scaled version of the source bitmap will now
            // be
            val targetRect: RectF = RectF(left, top, left + scaledWidth, top + scaledHeight)

            // Finally, we create a new bitmap of the specified size and draw our new,
            // scaled bitmap onto it.
            val dest: Bitmap = Bitmap.createBitmap(newWidth, newHeight, source.config)
            val canvas: Canvas = Canvas(dest)
            canvas.drawBitmap(source, null, targetRect, null)

            return dest
        }
    }
}
