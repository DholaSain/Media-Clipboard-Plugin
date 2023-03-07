import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream

class MediaClipboardPlugin {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun copyMediaToClipboard(base64Data: String, mimeType: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val decodedData = Base64.decode(base64Data, Base64.DEFAULT)

        val clipData = ClipData.newPlainText("", "")
        clipData.addItem(ClipData.Item(decodedData, mimeType.toClipDataFormat(), null))

        clipboard.setPrimaryClip(clipData)
    }

    private fun String.toClipDataFormat(): Int {
        return when (this) {
            "image/png" -> ClipData.MIMETYPE_TEXT_PLAIN
            "image/jpeg" -> ClipData.MIMETYPE_TEXT_PLAIN
            "image/gif" -> ClipData.MIMETYPE_TEXT_PLAIN
            "image/webp" -> ClipData.MIMETYPE_TEXT_PLAIN
            else -> ClipData.MIMETYPE_TEXT_PLAIN
        }
    }

    private fun Bitmap.toThumbnail(maxSize: Int): Bitmap {
        return ThumbnailUtils.extractThumbnail(this, maxSize, maxSize)
    }

    private fun Bitmap.toByteStream(format: Bitmap.CompressFormat): ByteArray {
        val outputStream = ByteArrayOutputStream()
        this.compress(format, 100, outputStream)
        return outputStream.toByteArray()
    }

    fun copyImageToClipboard(base64Data: String, mimeType: String) {
        val decodedData = Base64.decode(base64Data, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedData, 0, decodedData.size)
        val maxSize = if (bitmap.width > bitmap.height) bitmap.width else bitmap.height
        val thumbnail = bitmap.toThumbnail(maxSize)
        val byteArray = thumbnail.toByteStream(Bitmap.CompressFormat.PNG)
        copyMediaToClipboard(Base64.encodeToString(byteArray, Base64.DEFAULT), mimeType)
    }

    fun copyGifToClipboard(base64Data: String, mimeType: String) {
        val decodedData = Base64.decode(base64Data, Base64.DEFAULT)
        copyMediaToClipboard(Base64.encodeToString(decodedData, Base64.DEFAULT), mimeType)
    }

    fun copyWebpToClipboard(base64Data: String, mimeType: String) {
        val decodedData = Base64.decode(base64Data, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedData, 0, decodedData.size)
        val maxSize = if (bitmap.width > bitmap.height) bitmap.width else bitmap.height
        val thumbnail = bitmap.toThumbnail(maxSize)
        val byteArray = thumbnail.toByteStream(Bitmap.CompressFormat.WEBP)
        copyMediaToClipboard(Base64.encodeToString(byteArray, Base64.DEFAULT), mimeType)
    }
}
