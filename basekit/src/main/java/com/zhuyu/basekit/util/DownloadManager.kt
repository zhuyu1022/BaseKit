package com.zhuyu.basekit.util


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadManager(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    /**
     * 下载文件
     * @param url 文件地址
     * @param saveFile 文件存储地址
     * @param onStart 开始下载回调
     * @param onProgress 下载中回调
     * @param onComplete 下载完成回调
     * @param onError 下载失败回调
     */
    fun download(
        url: String,
        saveFile: File,
        onStart: () -> Unit = {},
        onProgress: (bytesRead: Long, contentLength: Long, percent: Int) -> Unit = { _, _, _ -> },
        onError: (Exception) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        scope.launch {
            try {
                withContext(Dispatchers.Main) {
                    onStart()
                }
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connect()
                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    throw IOException("Unexpected response code: ${connection.responseCode}")
                }
                val contentLength = connection.contentLength.toLong()
                val inputStream = connection.inputStream
                saveToFile(inputStream, saveFile, contentLength, onProgress)
                withContext(Dispatchers.Main) {
                    onComplete()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }

    private suspend fun saveToFile(
        inputStream: InputStream,
        saveFile: File,
        contentLength: Long,
        onProgress: (bytesRead: Long, contentLength: Long, percent: Int) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            FileOutputStream(saveFile).use { output ->
                val buffer = ByteArray(8 * 1024)
                var bytesRead: Int
                var totalBytesRead: Long = 0
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    val percent = if (contentLength > 0) {
                        (totalBytesRead * 100 / contentLength).toInt()
                    } else {
                        -1
                    }
                    withContext(Dispatchers.Main) {
                        onProgress(totalBytesRead, contentLength, percent)
                    }
                }
                output.flush()
            }
        }
    }
}


