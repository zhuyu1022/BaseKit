package com.zhuyu.basekit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zhuyu.basekit.base.BaseVbActivity
import com.zhuyu.basekit.databinding.ActivityDownloadBinding
import com.zhuyu.basekit.databinding.ActivityMainBinding
import com.zhuyu.basekit.ext.clickNoRepeat
import com.zhuyu.basekit.ui.DownloadActivity

class MainActivity : BaseVbActivity<ActivityMainBinding>() {



    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.downloadBtn.clickNoRepeat {
            startActivity(Intent(this, DownloadActivity::class.java))
        }
        mViewBind.activityResultBtn.clickNoRepeat {
            startActivity(Intent(this, ResultActivity::class.java))
        }
    }


}