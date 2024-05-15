package com.zhuyu.basekit

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.UriUtils
import com.zhuyu.basekit.databinding.ActivityResultBinding
import com.zhuyu.basekit.ext.clickNoRepeat
import com.zhuyu.basekit.ext.register
import com.zhuyu.basekit.ext.takePicture
import com.zhuyu.basekit.ext.takePicturePreview
import java.io.File

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            takePictureBtn.clickNoRepeat {
                takePicture {
                    previewImg.setImageURI(it)
                }
            }
            takePicturePreviewBtn.clickNoRepeat {
                takePicturePreview {
                    previewImg.setImageBitmap(it)
                }
            }
            openDocumentBtn.clickNoRepeat {
                register(contract = ActivityResultContracts.OpenDocument(), callback = {
                    Toast.makeText(this@ResultActivity, UriUtils.uri2File(it).path, Toast.LENGTH_SHORT).show()
                }).launch(arrayOf("image/*"))
            }
        }
    }

}