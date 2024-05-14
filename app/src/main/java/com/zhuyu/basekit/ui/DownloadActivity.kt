package com.zhuyu.basekit.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zhuyu.basekit.databinding.ActivityDownloadBinding
import com.zhuyu.basekit.util.DownloadManager
import java.io.File

class DownloadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startBtn.setOnClickListener {
            startDownload()
        }
    }

   private  fun startDownload() {
        val tempFile = File(externalCacheDir, "temp.apk")
        if (tempFile.exists()){
            tempFile.delete()
        }
        val url = "https://qqyxt.oss-cn-qingdao.aliyuncs.com/update/zhkt8/v8.0.3_zhkt_release_2024-04-24.apk"
        DownloadManager().download(url = url, saveFile = tempFile,
            onStart = {
                //开始下载
                Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show()
            },
            onComplete = {
                Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show()
            },
            onError = {
                Toast.makeText(this, "下载出错", Toast.LENGTH_SHORT).show()
            },
            onProgress = { bytesRead, contentLength, percent ->
                //下载进度
                binding.progressBar.progress = percent
            }

        )
    }
}