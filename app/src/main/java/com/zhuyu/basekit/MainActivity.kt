package com.zhuyu.basekit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zhuyu.basekit.databinding.ActivityDownloadBinding
import com.zhuyu.basekit.databinding.ActivityMainBinding
import com.zhuyu.basekit.ui.DownloadActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.downloadBtn.setOnClickListener {
            startActivity(Intent(this, DownloadActivity::class.java))
        }
    }
}